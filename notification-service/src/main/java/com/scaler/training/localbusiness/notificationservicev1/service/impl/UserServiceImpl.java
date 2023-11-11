package com.scaler.training.localbusiness.notificationservicev1.service.impl;

import com.scaler.training.localbusiness.notificationservicev1.repositories.UserRepository;
import com.scaler.training.localbusiness.notificationservicev1.service.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
