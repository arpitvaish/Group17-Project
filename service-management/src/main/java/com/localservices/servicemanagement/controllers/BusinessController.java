package com.localservices.servicemanagement.controllers;

import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.services.BusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(
            BusinessService businessService
    ) {
        this.businessService = businessService;
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<List<ServiceResponseDto>> getServicesByBusinessId(@PathVariable String businessId) throws NotFoundException {
        List<ServiceResponseDto> responseDtos = businessService.getServicesByBusinessId(UUID.fromString(businessId));
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

}
