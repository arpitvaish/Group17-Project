package dev.omkar.usermanagement.service;


import dev.omkar.usermanagement.Entity.User;
import dev.omkar.usermanagement.dto.UserDto;
import dev.omkar.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserDetails(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null;
        }
        return UserDto.from(userOptional.get());
    }
}
