package com.scaler.training.localbusiness.notificationservicev1.repositories;

import com.scaler.training.localbusiness.notificationservicev1.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByUserIdAndReviewIdAndRatingId(Long userId, Long reviewId, Long ratingId);
}
