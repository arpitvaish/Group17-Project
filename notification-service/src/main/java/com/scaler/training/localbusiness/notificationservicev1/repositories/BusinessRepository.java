package com.scaler.training.localbusiness.notificationservicev1.repositories;

import com.scaler.training.localbusiness.notificationservicev1.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}
