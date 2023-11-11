package com.scaler.training.localbusiness.notificationservicev1.service.impl;

import com.scaler.training.localbusiness.notificationservicev1.repositories.RatingRepository;
import com.scaler.training.localbusiness.notificationservicev1.service.RatingService;

public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
}
