package com.localservices.servicemanagement.controllers.unittesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localservices.servicemanagement.controllers.CategoryController;
import com.localservices.servicemanagement.dtos.ExceptionDto;
import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getServicesByCategoryName() throws Exception {
        String categoryName = "Auto";

        ServiceResponseDto responseDto1 = new ServiceResponseDto();
        responseDto1.setId(UUID.randomUUID());
        responseDto1.setServiceName("Car sales and repairs");
        responseDto1.setDescription("This service is for car");
        responseDto1.setBusinessName("ZXC business");
        responseDto1.setCategoryName("AUTOMOBILES");

        ServiceResponseDto responseDto2 = new ServiceResponseDto();
        responseDto2.setId(UUID.randomUUID());
        responseDto2.setServiceName("Bike sales and repairs");
        responseDto2.setDescription("This service is for Bikes");
        responseDto2.setBusinessName("ABC business");
        responseDto2.setCategoryName("AUTOMOBILES");

        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(responseDto1);
        responseDtos.add(responseDto2);

        when(
                categoryService.getServicesByCategoryName(categoryName)
        ).thenReturn(responseDtos);

        mockMvc.perform(
                get("/category/" + categoryName)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDtos))
        );
    }

    @Test
    public void returnEmptyArrayWhenThereIsNoServicesForGivenCategoryName() throws Exception {
        String categoryName = "Cloth";

        when(
                categoryService.getServicesByCategoryName(categoryName)
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/category/" + categoryName)
        ).andExpect(
                content().string("[]")
        );
    }
}
