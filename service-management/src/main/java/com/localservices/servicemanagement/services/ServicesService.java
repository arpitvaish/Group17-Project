package com.localservices.servicemanagement.services;

import com.localservices.servicemanagement.dtos.ServiceRequestDto;
import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.exceptions.UnableToCreateServiceException;

import java.util.List;
import java.util.UUID;

public interface ServicesService {
    ServiceResponseDto createService(
            String serviceName,
            String description,
            String businessName,
            String categoryName
    ) throws UnableToCreateServiceException;

    ServiceResponseDto getServiceById(
            UUID id
    ) throws NotFoundException;

    List<ServiceResponseDto> getAllServicesByName(String name);

    List<ServiceResponseDto> getAllServices();

    ServiceResponseDto updateServiceById(
            UUID id,
            String serviceName,
            String description,
            String categoryName
    ) throws NotFoundException;

    ServiceResponseDto deleteServiceById(UUID id) throws NotFoundException;
}
