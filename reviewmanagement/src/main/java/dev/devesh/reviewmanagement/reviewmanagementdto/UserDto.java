package dev.devesh.reviewmanagement.reviewmanagementdto;

import dev.devesh.reviewmanagement.reviewmanagementEntity.User;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @Email
    private String email;

    private String username;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());


        return userDto;
    }
}
