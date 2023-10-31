package com.localservices.servicemanagement.dtos;

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
}
