package com.scaler.training.localbusiness.notificationservicev1.repositories;

import com.scaler.training.localbusiness.notificationservicev1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
