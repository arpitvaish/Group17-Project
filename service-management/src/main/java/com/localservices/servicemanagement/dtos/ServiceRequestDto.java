package com.localservices.servicemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDto {
    private String serviceName;
    private String description;
    private String businessName;
    private String categoryName;
}
