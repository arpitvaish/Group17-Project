package dev.omkar.usermanagement.IntegrationTestWithDb;

import dev.omkar.usermanagement.dto.LoginRequestDto;
import dev.omkar.usermanagement.dto.RegisterRequestDto;
import dev.omkar.usermanagement.dto.UserDto;
import dev.omkar.usermanagement.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest

public class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Test
    public void testSignUpAndLogin() {

        RegisterRequestDto signUpRequest = new RegisterRequestDto();
        signUpRequest.setEmail("test2@example.com");
        signUpRequest.setUsername("testUser2");
        signUpRequest.setPassword("testPassword");

        UserDto signUpResponse = authService.signUp(signUpRequest.getEmail(), signUpRequest.getUsername(), signUpRequest.getPassword());
        assertNotNull(signUpResponse);
        assertEquals("test2@example.com", signUpResponse.getEmail());
        assertEquals("testUser2", signUpResponse.getUsername());

        // Login
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test2@example.com");
        loginRequest.setPassword("testPassword");

        UserDto loginResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword()).getBody();
        assertNotNull(loginResponse);
        assertEquals("test2@example.com", loginResponse.getEmail());
        assertEquals("testUser2", loginResponse.getUsername());


    }
}
