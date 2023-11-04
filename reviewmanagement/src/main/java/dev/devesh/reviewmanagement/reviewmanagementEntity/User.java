package dev.devesh.reviewmanagement.reviewmanagementEntity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseModel {

    private String email;
    private String username;
    private String password;
}
