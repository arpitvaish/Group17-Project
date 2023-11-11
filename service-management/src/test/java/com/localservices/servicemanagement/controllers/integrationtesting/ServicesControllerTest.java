package com.localservices.servicemanagement.controllers.integrationtesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localservices.servicemanagement.dtos.ServiceRequestDto;
import com.localservices.servicemanagement.dtos.UpdateServiceRequestDto;
import com.localservices.servicemanagement.models.Business;
import com.localservices.servicemanagement.models.Category;
import com.localservices.servicemanagement.models.ServiceModel;
import com.localservices.servicemanagement.repositories.BusinessRepositoryTest;
import com.localservices.servicemanagement.repositories.ServiceModelRepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ServicesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceModelRepositoryTest serviceModelRepositoryTest;

    @Autowired
    private BusinessRepositoryTest businessRepositoryTest;

    private ServiceModel savedServiceModel1;
    private ServiceModel savedServiceModel2;

    @BeforeEach
    public void uploadPreData() {
        Business business1 = Business.builder()
                .name("ABC business")
                .build();
        ServiceModel serviceModel1 = ServiceModel.builder()
                .serviceName("Mobile sales and repairs")
                .business(business1)
                .description("This service is for mobiles")
                .category(Category.ELECTRONICS)
                .build();
        savedServiceModel1 = serviceModelRepositoryTest.save(serviceModel1);

        Business business2 = Business.builder()
                .name("XYZ business")
                .build();
        ServiceModel serviceModel2 = ServiceModel.builder()
                .serviceName("Bike sales and repairs")
                .business(business2)
                .description("This service is for mobiles")
                .category(Category.AUTOMOBILES)
                .build();
        savedServiceModel2 = serviceModelRepositoryTest.save(serviceModel2);
    }

    @AfterEach
    public void deleteData() {
        serviceModelRepositoryTest.deleteAll();
        businessRepositoryTest.deleteAll();
    }

    @Test
    public void createService() throws Exception {
        ServiceRequestDto requestDto = new ServiceRequestDto();
        requestDto.setServiceName("Mens fashion");
        requestDto.setDescription("This service is for mens");
        requestDto.setBusinessName("ABC business");
        requestDto.setCategoryName("CLOTHING");

        mockMvc.perform(
                post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serviceName", is("Mens fashion")));

        assertEquals(3, serviceModelRepositoryTest.findAll().size());
        assertEquals(2, businessRepositoryTest.findAll().size());
    }

    @Test
    public void getServiceById() throws Exception {
        mockMvc.perform(
                get("/services/" + savedServiceModel1.getId())
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceName", is("Mobile sales and repairs")));

        assertEquals(2, serviceModelRepositoryTest.findAll().size());
        assertEquals(2, businessRepositoryTest.findAll().size());
    }

    @Test
    public void getAllServicesByName() throws Exception {
        mockMvc.perform(
                get("/services/search/" + "Mobile")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceName", is("Mobile sales and repairs")))
                .andExpect(jsonPath("$.length()", is(1)));

        assertEquals(2, serviceModelRepositoryTest.findAll().size());
        assertEquals(2, businessRepositoryTest.findAll().size());
    }

    @Test
    public void getAllServices() throws Exception {
        mockMvc.perform(
                get("/services")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceName", is("Mobile sales and repairs")))
                .andExpect(jsonPath("$.length()", is(2)));

        assertEquals(2, serviceModelRepositoryTest.findAll().size());
        assertEquals(2, businessRepositoryTest.findAll().size());
    }

    @Test
    public void updateServiceById() throws Exception {
        UpdateServiceRequestDto requestDto = new UpdateServiceRequestDto();
        requestDto.setServiceName("Mobile sales");
        requestDto.setDescription("This service is for mobiles");

        mockMvc.perform(
                put("/services/" + savedServiceModel1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceName", is("Mobile sales")));

        assertEquals(2, serviceModelRepositoryTest.findAll().size());
        assertEquals(2, businessRepositoryTest.findAll().size());
    }

    @Test
    public void deleteServiceById() throws Exception {
        mockMvc.perform(
                delete("/services/" + savedServiceModel1.getId())
        )
                .andExpect(status().isOk());

        assertEquals(1, serviceModelRepositoryTest.findAll().size());
        assertEquals(2, businessRepositoryTest.findAll().size());
    }
}
