package com.localservices.servicemanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localservices.servicemanagement.dtos.ExceptionDto;
import com.localservices.servicemanagement.dtos.ServiceRequestDto;
import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.dtos.UpdateServiceRequestDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.exceptions.UnableToCreateServiceException;
import com.localservices.servicemanagement.services.ServicesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest(ServicesController.class)
public class ServicesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicesService servicesService;

    @Autowired
    private ObjectMapper objectMapper;

    // createService - positive
    @Test
    public void createServiceShouldCreateNewService() throws Exception {
        ServiceRequestDto requestDto = new ServiceRequestDto();
        requestDto.setServiceName("Clothing Service");
        requestDto.setDescription("This service is for clothing");
        requestDto.setBusinessName("ABC business");
        requestDto.setCategoryName("Cloth");

        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(UUID.randomUUID());
        responseDto.setServiceName("Clothing Service");
        responseDto.setDescription("This service is for clothing");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("Cloth");

        when(
                servicesService.createService(
                        requestDto.getServiceName(),
                        requestDto.getDescription(),
                        requestDto.getBusinessName(),
                        requestDto.getCategoryName()
                )
        ).thenReturn(responseDto);

        mockMvc.perform(
                post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDto))
        );
    }

    // createService - negative
    @Test
    public void throwExceptionWhenServiceNameOrBusinessNameIsBlankToCreateService() throws Exception {
        ServiceRequestDto requestDto = new ServiceRequestDto();
        requestDto.setServiceName("");
        requestDto.setDescription("This service is for clothing");
        requestDto.setBusinessName("ABC business");
        requestDto.setCategoryName("Cloth");

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        exceptionDto.setMessage("Service name and Business name are can't be empty");

        when(
                servicesService.createService(
                        requestDto.getServiceName(),
                        requestDto.getDescription(),
                        requestDto.getBusinessName(),
                        requestDto.getCategoryName()
                )
        ).thenThrow(new UnableToCreateServiceException("Service name and Business name are can't be empty"));

        mockMvc.perform(
                post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(
            status().is(400)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(exceptionDto))
        );
    }

    // getServiceById - positive
    @Test
    public void getServiceByProvidedServiceId() throws Exception {
        UUID id = UUID.randomUUID();

        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(id);
        responseDto.setServiceName("Clothing Service");
        responseDto.setDescription("This service is for clothing");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("Cloth");

        when(
                servicesService.getServiceById(id)
        ).thenReturn(responseDto);

        mockMvc.perform(
                get("/services/"+id)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDto))
        );
    }

    // getServiceById - negative
    @Test
    public void throwExceptionWhenThereIsNoServiceWithGivenServiceId() throws Exception {
        UUID id = UUID.randomUUID();

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage("Service not found with id:" + id);

        when(
                servicesService.getServiceById(id)
        ).thenThrow(new NotFoundException("Service not found with id:" + id));

        mockMvc.perform(
                get("/services/"+id)
        ).andExpect(
                status().is(404)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(exceptionDto))
        );
    }

    // getAllServicesByName - positive
    @Test
    public void getServiceWhichServicesServiceNameIsLikeGivenString() throws Exception {
        String searchName = "Cloth";

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
                servicesService.getAllServicesByName(searchName)
        ).thenReturn(responseDtos);

        mockMvc.perform(
                get("/services/search/"+searchName)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDtos))
        );
    }

    // getAllServicesByName - negative
    @Test
    public void returnEmptyArrayWhenThereIsNoServicesServiceNameIsLikeGivenString() throws Exception {
        String searchName = "Cloth";

        when(
                servicesService.getAllServicesByName(searchName)
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/services/search/"+searchName)
        ).andExpect(
                content().string("[]")
        );
    }

    // getAllServices - positive
    @Test
    public void getAllServices() throws Exception {
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
                servicesService.getAllServices()
        ).thenReturn(responseDtos);

        mockMvc.perform(
                get("/services")
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDtos))
        );
    }

    // getAllServices - negative
    @Test
    public void returnEmptyArrayWhenThereIsNoServices() throws Exception {
        when(
                servicesService.getAllServices()
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/services")
        ).andExpect(
                content().string("[]")
        );
    }

    // updateServiceById - positive
    @Test
    public void updateServiceByServiceId() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateServiceRequestDto requestDto = new UpdateServiceRequestDto();
        requestDto.setServiceName("Clothing Service");
        requestDto.setDescription("This service is for clothing");
        requestDto.setCategoryName("Cloth");

        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(id);
        responseDto.setServiceName("Clothing Service");
        responseDto.setDescription("This service is for clothing");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("Cloth");

        when(
                servicesService.updateServiceById(
                        id,
                        requestDto.getServiceName(),
                        requestDto.getDescription(),
                        requestDto.getCategoryName()
                )
        ).thenReturn(responseDto);

        mockMvc.perform(
                put("/services/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDto))
        );
    }

    // updateServiceById - negative
    @Test
    public void throwExceptionWhenThereIsNoServiceToUpdateWithGivenServiceId() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateServiceRequestDto requestDto = new UpdateServiceRequestDto();
        requestDto.setServiceName("Clothing Service");
        requestDto.setDescription("This service is for clothing");
        requestDto.setCategoryName("Cloth");

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage("Service not found with id:" + id);

        when(
                servicesService.updateServiceById(
                        id,
                        requestDto.getServiceName(),
                        requestDto.getDescription(),
                        requestDto.getCategoryName()
                )
        ).thenThrow(new NotFoundException("Service not found with id:" + id));

        mockMvc.perform(
                put("/services/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(
                status().is(404)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(exceptionDto))
        );
    }

    // deleteServiceById - positive
    @Test
    public void deleteServiceByProvidedServiceId() throws Exception {
        UUID id = UUID.randomUUID();

        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(id);
        responseDto.setServiceName("Clothing Service");
        responseDto.setDescription("This service is for clothing");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("Cloth");

        when(
                servicesService.deleteServiceById(id)
        ).thenReturn(responseDto);

        mockMvc.perform(
                delete("/services/"+id)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDto))
        );
    }

    // deleteServiceById - negative
    @Test
    public void throwExceptionWhenThereIsNoServiceToDeleteWithGivenServiceId() throws Exception {
        UUID id = UUID.randomUUID();

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage("Service not found with id:" + id);

        when(
                servicesService.deleteServiceById(id)
        ).thenThrow(new NotFoundException("Service not found with id:" + id));

        mockMvc.perform(
                delete("/services/"+id)
        ).andExpect(
                status().is(404)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(exceptionDto))
        );
    }
}
