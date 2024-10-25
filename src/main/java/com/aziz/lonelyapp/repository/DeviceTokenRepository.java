package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.DeviceTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceTokenEntity, Long> {
    Boolean existsByDeviceToken(String deviceToken);

    List<DeviceTokenEntity> findByUserId(String userId);
}
