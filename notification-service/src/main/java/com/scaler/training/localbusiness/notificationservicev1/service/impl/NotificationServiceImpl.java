package com.scaler.training.localbusiness.notificationservicev1.service.impl;

import com.scaler.training.localbusiness.notificationservicev1.models.Notification;
import com.scaler.training.localbusiness.notificationservicev1.repositories.NotificationRepository;
import com.scaler.training.localbusiness.notificationservicev1.service.NotificationService;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotificationByUserIdAndReviewIdAndRatingId(Long userId, Long reviewId, Long ratingId) {
        return notificationRepository.findByUserIdAndReviewIdAndRatingId(userId, reviewId, ratingId);
    }

}
