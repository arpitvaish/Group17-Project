package com.localservices.servicemanagement.dtos;

import com.localservices.servicemanagement.models.ServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponseDto {
    private UUID id;
    private String serviceName;
    private String description;
    private String businessName;
    private String categoryName;

    public static ServiceResponseDto from(ServiceModel service) {
        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(service.getId());
        responseDto.setServiceName(service.getServiceName());
        responseDto.setDescription(service.getDescription());
        responseDto.setBusinessName(service.getBusiness().getName());
        responseDto.setCategoryName(service.getCategory().name());
        return responseDto;
    }
}
