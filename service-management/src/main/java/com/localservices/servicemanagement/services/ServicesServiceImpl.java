package com.localservices.servicemanagement.services;

import com.localservices.servicemanagement.dtos.ServiceRequestDto;
import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.exceptions.UnableToCreateServiceException;
import com.localservices.servicemanagement.models.Business;
import com.localservices.servicemanagement.models.Category;
import com.localservices.servicemanagement.models.ServiceModel;
import com.localservices.servicemanagement.repositories.ServiceModelRepository;
import com.localservices.servicemanagement.repositories.BusinessRepository;
import com.localservices.servicemanagement.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicesServiceImpl implements ServicesService{

    private final ServiceModelRepository serviceRepository;
    private final BusinessRepository businessRepository;
    private final CategoryRepository categoryRepository;

    public ServicesServiceImpl(
            ServiceModelRepository serviceRepository,
            BusinessRepository businessRepository,
            CategoryRepository categoryRepository
    ) {
        this.serviceRepository = serviceRepository;
        this.businessRepository = businessRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ServiceResponseDto createService(
            String serviceName,
            String description,
            String businessName,
            String categoryName
    ) throws UnableToCreateServiceException {
        if (serviceName.isBlank() || businessName.isBlank()) {
            throw new UnableToCreateServiceException("Service name and Business name are can't be empty");
        }

        Optional<Business> optionalBusiness = businessRepository.findByName(businessName);
        Business business = new Business();
        if(optionalBusiness.isPresent()) {
           business = optionalBusiness.get();
        }
        else {
            business.setName(businessName);
        }

        Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
        Category category = new Category();
        if(optionalCategory.isPresent()) {
            category = optionalCategory.get();
        }
        else {
            category.setName(categoryName);
        }

        Optional<ServiceModel> optionalService = serviceRepository.findByServiceNameAndBusinessNameAndCategoryName(
                serviceName, businessName, categoryName
        );
        ServiceModel service = new ServiceModel();
        if(optionalService.isPresent()) {
            throw new UnableToCreateServiceException("Already service exists with these details");
        }
        service.setServiceName(serviceName);
        service.setDescription(description);
        service.setBusiness(business);
        service.setCategory(category);

        ServiceModel savedService = serviceRepository.save(service);

        return getServiceResponseDtoFromServiceModel(savedService);
    }

    @Override
    public ServiceResponseDto getServiceById(UUID id) throws NotFoundException {
        Optional<ServiceModel> optionalService = serviceRepository.findById(id);
        if(!optionalService.isPresent()) {
            throw new NotFoundException("Service not found with id:" + id);
        }

        ServiceModel service = optionalService.get();

        return getServiceResponseDtoFromServiceModel(service);
    }

    @Override
    public List<ServiceResponseDto> getAllServicesByName(String name) {
        List<ServiceModel> services = serviceRepository.findByServiceNameContaining(name);

        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        services.forEach(service -> {
            responseDtos.add(getServiceResponseDtoFromServiceModel(service));
        });

        return responseDtos;
    }

    @Override
    public List<ServiceResponseDto> getAllServices() {
        List<ServiceModel> services = serviceRepository.findAll();

        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        services.forEach(service -> {
            responseDtos.add(getServiceResponseDtoFromServiceModel(service));
        });

        return responseDtos;
    }

    @Override
    public ServiceResponseDto updateServiceById(
            UUID id,
            String serviceName,
            String description,
            String categoryName
    ) throws NotFoundException {
        Optional<ServiceModel> optionalService = serviceRepository.findById(id);
        if(!optionalService.isPresent()) {
            throw new NotFoundException("Service not found with id:" + id);
        }
        ServiceModel service = optionalService.get();
        if(!serviceName.isBlank()) {
            service.setServiceName(serviceName);
        }
        if(!description.isBlank()) {
            service.setDescription(description);
        }
        if(!categoryName.isBlank()) {
            Optional<Category> optionalCategory = categoryRepository.findById(service.getCategory().getId());
            if(optionalCategory.isPresent() && optionalCategory.get().getServices().size() == 1) {
                service.getCategory().setName(categoryName);
            }
            else {
                Category category = new Category();
                category.setName(categoryName);
                service.setCategory(category);
            }
        }
        ServiceModel savedService = serviceRepository.save(service);

        return getServiceResponseDtoFromServiceModel(savedService);
    }

    @Override
    public ServiceResponseDto deleteServiceById(UUID id) throws NotFoundException {
        Optional<ServiceModel> optionalService = serviceRepository.findById(id);
        if(!optionalService.isPresent()) {
            throw new NotFoundException("Service not found with id:" + id);
        }
        ServiceModel service = optionalService.get();
        serviceRepository.delete(service);
        return getServiceResponseDtoFromServiceModel(service);
    }

    private static ServiceResponseDto getServiceResponseDtoFromServiceModel(ServiceModel savedService) {
        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(savedService.getId());
        responseDto.setServiceName(savedService.getServiceName());
        responseDto.setDescription(savedService.getDescription());
        responseDto.setBusinessName(savedService.getBusiness().getName());
        responseDto.setCategoryName(savedService.getCategory().getName());
        return responseDto;
    }
}
