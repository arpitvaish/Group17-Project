package dev.devesh.reviewmanagement.reviewmanagementservice;


import dev.devesh.reviewmanagement.reviewmanagementEntity.User;
import dev.devesh.reviewmanagement.reviewmanagementdto.UserDto;
import dev.devesh.reviewmanagement.reviewmanagementrepository.UserRepository;
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
