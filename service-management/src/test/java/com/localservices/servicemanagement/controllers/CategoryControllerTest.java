package com.localservices.servicemanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void getServicesByCategoryId() throws Exception {
        UUID id = UUID.randomUUID();

        ServiceResponseDto responseDto1 = new ServiceResponseDto();
        responseDto1.setId(UUID.randomUUID());
        responseDto1.setServiceName("Clothing Service");
        responseDto1.setDescription("This service is for clothing");
        responseDto1.setBusinessName("ABC business");
        responseDto1.setCategoryName("Cloth");

        ServiceResponseDto responseDto2 = new ServiceResponseDto();
        responseDto2.setId(UUID.randomUUID());
        responseDto2.setServiceName("Mens Clothing Service");
        responseDto2.setDescription("This service is for clothing");
        responseDto2.setBusinessName("ZXC business");
        responseDto2.setCategoryName("Cloth");

        List<ServiceResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(responseDto1);
        responseDtos.add(responseDto2);

        when(
                categoryService.getServicesByCategoryId(id)
        ).thenReturn(responseDtos);

        mockMvc.perform(
                get("/category/" + id)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDtos))
        );
    }

    @Test
    public void throwExceptionWhenThereIsNoCategoryForGivenCategoryId() throws Exception {
        UUID id = UUID.randomUUID();

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage("Category not found with id:" + id);

        when(
                categoryService.getServicesByCategoryId(id)
        ).thenThrow(new NotFoundException("Category not found with id:" + id));

        mockMvc.perform(
                get("/category/" + id)
        ).andExpect(
                status().is(404)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(exceptionDto))
        );
    }

    @Test
    public void returnEmptyArrayWhenThereIsNoServicesForGivenCategoryId() throws Exception {
        UUID id = UUID.randomUUID();

        when(
                categoryService.getServicesByCategoryId(id)
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/category/" + id)
        ).andExpect(
                content().string("[]")
        );
    }
}
