package com.localservices.servicemanagement.services;

import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.exceptions.UnableToCreateServiceException;
import com.localservices.servicemanagement.models.Category;
import com.localservices.servicemanagement.models.ServiceModel;
import com.localservices.servicemanagement.repositories.ServiceModelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ServiceModelRepository serviceRepository;

    public CategoryServiceImpl(
            ServiceModelRepository serviceRepository
    ) {
        this.serviceRepository = serviceRepository;
    }
    @Override
    public List<ServiceResponseDto> getServicesByCategoryName(String categoryName) {
        List<Category> categories = new ArrayList<>();
        categoryName = categoryName.toUpperCase();

        for(Category category: Category.values()) {
            if(category.name().contains(categoryName)) {
                categories.add(category);
                break;
            }
        }

        List<ServiceModel> services = serviceRepository.findByCategoryIn(categories);

        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        services.forEach(service -> responseDtos.add(ServiceResponseDto.from(service)));
        return responseDtos;
    }
}
