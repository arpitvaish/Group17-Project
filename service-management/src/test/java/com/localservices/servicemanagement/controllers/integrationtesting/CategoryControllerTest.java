package com.localservices.servicemanagement.controllers.integrationtesting;

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
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
    public void getServicesByCategoryName() throws Exception {
        mockMvc.perform(
                get("/category/" + Category.AUTOMOBILES)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        assertEquals(2, serviceModelRepositoryTest.findAll().size());
        assertEquals(2, businessRepositoryTest.findAll().size());
    }
}
