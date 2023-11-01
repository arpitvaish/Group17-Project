package dev.devesh.reviewmanagement.reviewmanagementcontroller;
import dev.devesh.reviewmanagement.reviewmanagementdto.UserDto;
import dev.devesh.reviewmanagement.reviewmanagementservice.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @Before
    public void setup() {

        reset(authService);
    }

    @Test
    public void testSignupSuccess() {

        UserDto expectedUserDto = new UserDto();


        when(authService.signUp("test@example.com", "testUser", "password")).thenReturn(expectedUserDto);

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"email\": \"test@example.com\", \"username\": \"testUser\", \"password\": \"password\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {

        }
    }

    @Test
    public void testLoginFailure() {

        when(authService.login("test@example.com", "wrongPassword")).thenReturn(null);

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"email\": \"test@example.com\", \"password\": \"wrongPassword\"}"))
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {

        }
    }
}
