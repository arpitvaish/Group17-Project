package com.scaler.training.localbusiness.notificationservicev1.repositories;

import com.scaler.training.localbusiness.notificationservicev1.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
