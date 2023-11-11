package com.localservices.servicemanagement.controllers;

import com.localservices.servicemanagement.dtos.ServiceRequestDto;
import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.dtos.UpdateServiceRequestDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.exceptions.UnableToCreateServiceException;
import com.localservices.servicemanagement.services.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ServiceResponseDto> createService(@RequestBody ServiceRequestDto serviceRequestDto)
            throws UnableToCreateServiceException {
        ServiceResponseDto responseDto = servicesService.createService(
                serviceRequestDto.getServiceName(),
                serviceRequestDto.getDescription(),
                serviceRequestDto.getBusinessName(),
                serviceRequestDto.getCategoryName()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable String serviceId) throws NotFoundException {
        ServiceResponseDto responseDto = servicesService.getServiceById(UUID.fromString(serviceId));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/search/{serviceName}")
    public ResponseEntity<List<ServiceResponseDto>> getAllServicesByName(@PathVariable String serviceName) throws NotFoundException {
        List<ServiceResponseDto> responseDtos = servicesService.getAllServicesByName(serviceName);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
        List<ServiceResponseDto> responseDtos = servicesService.getAllServices();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> updateServiceById(
            @PathVariable String serviceId,
            @RequestBody UpdateServiceRequestDto updateServiceRequestDto
    ) throws NotFoundException {
        ServiceResponseDto responseDto = servicesService.updateServiceById(
                UUID.fromString(serviceId),
                updateServiceRequestDto.getServiceName(),
                updateServiceRequestDto.getDescription()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> deleteServiceById(@PathVariable String serviceId) throws NotFoundException {
        ServiceResponseDto responseDto = servicesService.deleteServiceById(UUID.fromString(serviceId));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
