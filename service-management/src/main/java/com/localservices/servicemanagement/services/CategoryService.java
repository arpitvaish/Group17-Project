package com.localservices.servicemanagement.services;

import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<ServiceResponseDto> getServicesByCategoryName(String categoryName);
}
