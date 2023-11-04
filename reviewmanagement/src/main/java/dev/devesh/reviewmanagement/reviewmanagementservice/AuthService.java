package dev.devesh.reviewmanagement.reviewmanagementservice;

import dev.devesh.reviewmanagement.reviewmanagementrepository.UserRepository;
import dev.devesh.reviewmanagement.reviewmanagementEntity.User;
import dev.devesh.reviewmanagement.reviewmanagementdto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    private boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
    public UserDto signUp(String email, String username, String password ) {

        if (StringUtils.isAnyBlank(email, username, password)) {
            throw new IllegalArgumentException("Email, username, and password must not be blank.");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public ResponseEntity<UserDto> login(String email,String password) {


        if (StringUtils.isAnyBlank(email, password)) {
            throw new IllegalArgumentException("Email and password must not be blank.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);


        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Wrong username or password.");
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong username password");
        }
        String token = RandomStringUtils.randomAlphanumeric(30);

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();
        UserDto userDto = UserDto.from(user);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        return response;

    }
}
