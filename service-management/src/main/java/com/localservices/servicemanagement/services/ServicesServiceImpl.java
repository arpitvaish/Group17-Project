package com.localservices.servicemanagement.services;

import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.exceptions.UnableToCreateServiceException;
import com.localservices.servicemanagement.models.Business;
import com.localservices.servicemanagement.models.Category;
import com.localservices.servicemanagement.models.ServiceModel;
import com.localservices.servicemanagement.repositories.ServiceModelRepository;
import com.localservices.servicemanagement.repositories.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicesServiceImpl implements ServicesService{

    private final ServiceModelRepository serviceRepository;
    private final BusinessRepository businessRepository;

    public ServicesServiceImpl(
            ServiceModelRepository serviceRepository,
            BusinessRepository businessRepository
    ) {
        this.serviceRepository = serviceRepository;
        this.businessRepository = businessRepository;
    }

    @Override
    public ServiceResponseDto createService(
            String serviceName,
            String description,
            String businessName,
            String categoryName
    ) throws UnableToCreateServiceException {
        if (serviceName.isBlank() || businessName.isBlank() || categoryName.isBlank()) {
            throw new UnableToCreateServiceException("Service name, Business name and Category name are cant be empty");
        }

        Optional<Business> optionalBusiness = businessRepository.findByName(businessName);
        Business business = new Business();
        if(optionalBusiness.isPresent()) {
           business = optionalBusiness.get();
        }
        else {
            business.setName(businessName);
        }

        categoryName = categoryName.toUpperCase();
        boolean isCategoryExists = false;
        for(Category category: Category.values()) {
            if(category.name().equals(categoryName)) {
                isCategoryExists = true;
                break;
            }
        }
        if(!isCategoryExists) {
            throw new UnableToCreateServiceException("Unable to create service with this category");
        }

        Optional<ServiceModel> optionalService = serviceRepository.findByServiceNameAndBusiness_NameAndCategoryIs(
                serviceName, businessName, Category.valueOf(categoryName)
        );
        ServiceModel service = new ServiceModel();
        if(optionalService.isPresent()) {
            throw new UnableToCreateServiceException("Already service exists with these details");
        }
        service.setServiceName(serviceName);
        service.setDescription(description);
        service.setBusiness(business);
        service.setCategory(Category.valueOf(categoryName));

        ServiceModel savedService = serviceRepository.save(service);

        return ServiceResponseDto.from(savedService);
    }

    @Override
    public ServiceResponseDto getServiceById(UUID id) throws NotFoundException {
        Optional<ServiceModel> optionalService = serviceRepository.findById(id);
        if(optionalService.isEmpty()) {
            throw new NotFoundException("Service not found with id:" + id);
        }

        ServiceModel service = optionalService.get();

        return ServiceResponseDto.from(service);
    }

    @Override
    public List<ServiceResponseDto> getAllServicesByName(String name) {
        List<ServiceModel> services = serviceRepository.findByServiceNameContaining(name);

        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        services.forEach(service -> {
            responseDtos.add(ServiceResponseDto.from(service));
        });

        return responseDtos;
    }

    @Override
    public List<ServiceResponseDto> getAllServices() {
        List<ServiceModel> services = serviceRepository.findAll();

        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        services.forEach(service -> {
            responseDtos.add(ServiceResponseDto.from(service));
        });

        return responseDtos;
    }

    @Override
    public ServiceResponseDto updateServiceById(
            UUID id,
            String serviceName,
            String description
    ) throws NotFoundException {
        Optional<ServiceModel> optionalService = serviceRepository.findById(id);
        if(optionalService.isEmpty()) {
            throw new NotFoundException("Service not found with id:" + id);
        }
        ServiceModel service = optionalService.get();
        if(!serviceName.isBlank()) {
            service.setServiceName(serviceName);
        }
        if(!description.isBlank()) {
            service.setDescription(description);
        }
        ServiceModel savedService = serviceRepository.save(service);

        return ServiceResponseDto.from(savedService);
    }

    @Override
    public ServiceResponseDto deleteServiceById(UUID id) throws NotFoundException {
        Optional<ServiceModel> optionalService = serviceRepository.findById(id);
        if(optionalService.isEmpty()) {
            throw new NotFoundException("Service not found with id:" + id);
        }
        ServiceModel service = optionalService.get();
        serviceRepository.delete(service);
        return ServiceResponseDto.from(service);
    }

}
