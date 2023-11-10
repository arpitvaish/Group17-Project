package com.localservices.servicemanagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Business extends BaseModel{
    private String name;

    @OneToMany(mappedBy = "business")
    @Fetch(FetchMode.SELECT)
    private List<ServiceModel> services;
}
