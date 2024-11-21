package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.RefreshToken;
import com.aziz.lonelyapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface represents a repository for managing refresh tokens in the application.
 * It extends the JpaRepository interface provided by Spring Data JPA, allowing for CRUD operations.
 *
 * @author YourName
 * @since 1.0
 */
public interface TokenRepository extends JpaRepository<RefreshToken, Integer> {

    /**
     * Finds a refresh token by its token value.
     *
     * @param name The token value to search for.
     * @return An Optional containing the refresh token if found, otherwise an empty Optional.
     */
    Optional<RefreshToken> findByToken(String name);

    /**
     * Checks if a refresh token with the given token value exists in the repository.
     *
     * @param name The token value to search for.
     * @return True if a refresh token with the given token value exists, otherwise false.
     */
    Boolean existsByToken(String name);
}
