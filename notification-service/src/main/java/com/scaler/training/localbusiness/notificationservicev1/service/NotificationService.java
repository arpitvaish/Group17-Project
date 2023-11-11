package com.scaler.training.localbusiness.notificationservicev1.service;

import com.scaler.training.localbusiness.notificationservicev1.models.Notification;

import java.util.List;

public interface NotificationService {
    public List<Notification> getNotificationByUserIdAndReviewIdAndRatingId(Long userId, Long reviewId, Long ratingId);
}
