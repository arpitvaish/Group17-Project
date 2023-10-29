package com.localservices.servicemanagement.services;

import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.models.Business;
import com.localservices.servicemanagement.models.Category;
import com.localservices.servicemanagement.models.ServiceModel;
import com.localservices.servicemanagement.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ServiceResponseDto> getServicesByCategoryId(UUID id) throws NotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found with id:" + id);
        }

        Category category = optionalCategory.get();
        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        category.getServices().forEach(service -> {
            responseDtos.add(getServiceResponseDtoFromServiceModel(service));
        });

        return responseDtos;
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
