package com.localservices.servicemanagement.controllers;

import com.localservices.servicemanagement.dtos.ServiceRequestDto;
import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.dtos.UpdateServiceRequestDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.exceptions.UnableToCreateServiceException;
import com.localservices.servicemanagement.services.ServicesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ServicesController {

    private final ServicesService servicesService;

    public ServicesController(
            ServicesService servicesService
    ) {
        this.servicesService = servicesService;
    }

    @PostMapping
    public ServiceResponseDto createService(@RequestBody ServiceRequestDto serviceRequestDto)
            throws UnableToCreateServiceException {
        ServiceResponseDto responseDto = servicesService.createService(
                serviceRequestDto.getServiceName(),
                serviceRequestDto.getDescription(),
                serviceRequestDto.getBusinessName(),
                serviceRequestDto.getCategoryName()
        );
        return responseDto;
    }

    @GetMapping("/{serviceId}")
    public ServiceResponseDto getServiceById(@PathVariable String serviceId) throws NotFoundException {
        ServiceResponseDto responseDto = servicesService.getServiceById(UUID.fromString(serviceId));
        return responseDto;
    }

    @GetMapping("/search/{serviceName}")
    public List<ServiceResponseDto> getAllServicesByName(@PathVariable String serviceName) throws NotFoundException {
        List<ServiceResponseDto> responseDtos = servicesService.getAllServicesByName(serviceName);
        return responseDtos;
    }

    @GetMapping
    public List<ServiceResponseDto> getAllServices() {
        List<ServiceResponseDto> responseDtos = servicesService.getAllServices();
        return responseDtos;
    }

    @PutMapping("/{serviceId}")
    public ServiceResponseDto updateServiceById(
            @PathVariable String serviceId,
            @RequestBody UpdateServiceRequestDto updateServiceRequestDto
    ) throws NotFoundException {
        ServiceResponseDto responseDto = servicesService.updateServiceById(
                UUID.fromString(serviceId),
                updateServiceRequestDto.getServiceName(),
                updateServiceRequestDto.getDescription(),
                updateServiceRequestDto.getCategoryName()
        );
        return responseDto;
    }

    @DeleteMapping("/{serviceId}")
    public ServiceResponseDto deleteServiceById(@PathVariable String serviceId) throws NotFoundException {
        ServiceResponseDto responseDto = servicesService.deleteServiceById(UUID.fromString(serviceId));
        return responseDto;
    }
}
