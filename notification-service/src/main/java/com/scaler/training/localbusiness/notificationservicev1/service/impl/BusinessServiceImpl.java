package com.scaler.training.localbusiness.notificationservicev1.service.impl;

import com.scaler.training.localbusiness.notificationservicev1.repositories.BusinessRepository;
import com.scaler.training.localbusiness.notificationservicev1.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {

    private BusinessRepository businessRepository;

    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }
}
