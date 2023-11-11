package com.localservices.servicemanagement.services;

import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.models.Business;
import com.localservices.servicemanagement.models.ServiceModel;
import com.localservices.servicemanagement.repositories.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusinessServiceImpl implements BusinessService{

    private final BusinessRepository businessRepository;

    public BusinessServiceImpl(
            BusinessRepository businessRepository
    ) {
        this.businessRepository = businessRepository;
    }
    @Override
    public List<ServiceResponseDto> getServicesByBusinessId(UUID id) throws NotFoundException {
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        if(optionalBusiness.isEmpty()) {
            throw new NotFoundException("Business not found with id:" + id);
        }

        Business business = optionalBusiness.get();
        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        business.getServices().forEach(service -> {
            responseDtos.add(ServiceResponseDto.from(service));
        });

        return responseDtos;
    }
}
