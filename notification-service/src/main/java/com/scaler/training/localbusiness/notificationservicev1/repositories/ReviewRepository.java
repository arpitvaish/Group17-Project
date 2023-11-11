package com.scaler.training.localbusiness.notificationservicev1.repositories;

import com.scaler.training.localbusiness.notificationservicev1.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
