package dev.devesh.reviewmanagement.reviewmanagementcontroller;

import dev.devesh.reviewmanagement.reviewmanagementdto.LoginRequestDto;
import dev.devesh.reviewmanagement.reviewmanagementdto.RegisterRequestDto;
import dev.devesh.reviewmanagement.reviewmanagementdto.UserDto;
import dev.devesh.reviewmanagement.reviewmanagementservice.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Log in with email and password")
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) {
        ResponseEntity<UserDto> response = authService.login(request.getEmail(), request.getPassword());

        if(response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            return authService.login(request.getEmail(),request.getPassword());
        }

    }

    @Operation(summary = "register a new User")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> signUp(@RequestBody RegisterRequestDto request) {
        UserDto userDto = authService.signUp(request.getEmail(),request.getUsername(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}