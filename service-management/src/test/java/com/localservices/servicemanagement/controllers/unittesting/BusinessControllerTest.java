package com.localservices.servicemanagement.controllers.unittesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localservices.servicemanagement.controllers.BusinessController;
import com.localservices.servicemanagement.dtos.ExceptionDto;
import com.localservices.servicemanagement.dtos.ServiceResponseDto;
import com.localservices.servicemanagement.exceptions.NotFoundException;
import com.localservices.servicemanagement.services.BusinessService;
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

@WebMvcTest(BusinessController.class)
public class BusinessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessService businessService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getServicesByBusinessId() throws Exception {
        UUID id = UUID.randomUUID();

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
                businessService.getServicesByBusinessId(id)
        ).thenReturn(responseDtos);

        mockMvc.perform(
                get("/business/" + id)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(responseDtos))
        );
    }

    @Test
    public void throwExceptionWhenThereIsNoBusinessForGivenBusinessId() throws Exception {
        UUID id = UUID.randomUUID();

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage("Business not found with id:" + id);

        when(
                businessService.getServicesByBusinessId(id)
        ).thenThrow(new NotFoundException("Business not found with id:" + id));

        mockMvc.perform(
                get("/business/" + id)
        ).andExpect(
                status().is(404)
        ).andExpect(
                content().string(objectMapper.writeValueAsString(exceptionDto))
        );
    }

    @Test
    public void returnEmptyArrayWhenThereIsNoServicesForGivenBusinessId() throws Exception {
        UUID id = UUID.randomUUID();

        when(
                businessService.getServicesByBusinessId(id)
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/business/" + id)
        ).andExpect(
                content().string("[]")
        );
    }
}
