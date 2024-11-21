package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.DeviceTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for managing {@link DeviceTokenEntity} entities.
 * It extends Spring Data JPA's {@link JpaRepository} interface, providing basic CRUD operations
 * and additional methods for custom queries.
 */
public interface DeviceTokenRepository extends JpaRepository<DeviceTokenEntity, Long> {

    /**
     * Checks if a device token exists in the repository.
     *
     * @param deviceToken the device token to check
     * @return {@code true} if the device token exists, {@code false} otherwise
     */
    Boolean existsByDeviceToken(String deviceToken);

    /**
     * Retrieves a list of device token entities associated with a given user ID.
     *
     * @param userId the user ID to search for
     * @return a list of {@link DeviceTokenEntity} objects associated with the user ID
     */
    List<DeviceTokenEntity> findByUserId(String userId);
}
