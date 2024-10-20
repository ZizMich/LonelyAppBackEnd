package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.DeviceTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceTokenEntity, Long> {
    Boolean existsByDeviceToken(String deviceToken);
}
