package dev.arif.ratingservice.controller;

import dev.arif.ratingservice.dto.RatingDto;
import dev.arif.ratingservice.service.RatingService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;
    private Object Matchers;


    @Test
    public void  getRatingServiceProductId() throws Exception
    {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setProductId("Iphone");
        ratingDto.setRating(5);
        ratingDto.setComment("Amazing");
        String id  = ratingDto.getProductId();

        
    }
}
