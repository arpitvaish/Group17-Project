package com.scaler.training.localbusiness.notificationservicev1.service.impl;

import com.scaler.training.localbusiness.notificationservicev1.repositories.ReviewRepository;
import com.scaler.training.localbusiness.notificationservicev1.service.ReviewService;

public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
}
