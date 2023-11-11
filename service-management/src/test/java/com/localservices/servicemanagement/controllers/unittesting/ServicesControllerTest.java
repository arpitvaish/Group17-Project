package com.localservices.servicemanagement.controllers.unittesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localservices.servicemanagement.controllers.ServicesController;
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
        requestDto.setServiceName("Mobile sales and repairs");
        requestDto.setDescription("This service is for mobiles");
        requestDto.setBusinessName("ABC business");
        requestDto.setCategoryName("ELECTRONICS");

        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(UUID.randomUUID());
        responseDto.setServiceName("Mobile sales and repairs");
        responseDto.setDescription("This service is for mobiles");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("ELECTRONICS");

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
        requestDto.setDescription("This service is for mobiles");
        requestDto.setBusinessName("ABC business");
        requestDto.setCategoryName("ELECTRONICS");

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        exceptionDto.setMessage("Service name and Business name are cant be empty");

        when(
                servicesService.createService(
                        requestDto.getServiceName(),
                        requestDto.getDescription(),
                        requestDto.getBusinessName(),
                        requestDto.getCategoryName()
                )
        ).thenThrow(new UnableToCreateServiceException("Service name and Business name are cant be empty"));

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
        responseDto.setServiceName("Mobile sales and repairs");
        responseDto.setDescription("This service is for mobiles");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("ELECTRONICS");

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
        String searchName = "repairs";

        ServiceResponseDto responseDto1 = new ServiceResponseDto();
        responseDto1.setId(UUID.randomUUID());
        responseDto1.setServiceName("Mobile sales and repairs");
        responseDto1.setDescription("This service is for mobiles");
        responseDto1.setBusinessName("ABC business");
        responseDto1.setCategoryName("ELECTRONICS");

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
        String searchName = "salon";

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
        responseDto1.setServiceName("Mobile sales and repairs");
        responseDto1.setDescription("This service is for mobiles");
        responseDto1.setBusinessName("ABC business");
        responseDto1.setCategoryName("ELECTRONICS");

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
        requestDto.setServiceName("Mobile sales and repairs");
        requestDto.setDescription("This service is for mobiles");

        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(id);
        responseDto.setServiceName("Mobile sales and repairs");
        responseDto.setDescription("This service is for mobiles");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("ELECTRONICS");

        when(
                servicesService.updateServiceById(
                        id,
                        requestDto.getServiceName(),
                        requestDto.getDescription()
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
        requestDto.setServiceName("Mobile sales and repairs");
        requestDto.setDescription("This service is for mobiles");

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage("Service not found with id:" + id);

        when(
                servicesService.updateServiceById(
                        id,
                        requestDto.getServiceName(),
                        requestDto.getDescription()
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
        responseDto.setServiceName("Mobile sales and repairs");
        responseDto.setDescription("This service is for mobiles");
        responseDto.setBusinessName("ABC business");
        responseDto.setCategoryName("ELECTRONICS");

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
