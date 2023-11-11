package com.localservices.servicemanagement.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "service")
public class ServiceModel extends BaseModel{
    private String serviceName;
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "business")
    private Business business;

    @Enumerated
    private Category category;
}
