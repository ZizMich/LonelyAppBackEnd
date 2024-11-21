package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * This interface represents a repository for managing {@link UserEntity} entities.
 * It extends Spring Data JPA's {@link JpaRepository} interface, providing basic CRUD operations
 * and additional methods for custom queries.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Finds a {@link UserEntity} by its name.
     *
     * @param name the name of the user to find
     * @return an {@link Optional} containing the found {@link UserEntity}, or an empty {@link Optional} if not found
     */
    Optional<UserEntity> findByName(String name);

    /**
     * Finds a {@link UserEntity} by its email.
     *
     * @param email the email of the user to find
     * @return an {@link Optional} containing the found {@link UserEntity}, or an empty {@link Optional} if not found
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Finds a {@link UserEntity} by its id.
     *
     * @param id the id of the user to find
     * @return an {@link Optional} containing the found {@link UserEntity}, or an empty {@link Optional} if not found
     */
    Optional<UserEntity> findById(String id);

    /**
     * Checks if a {@link UserEntity} with the given email exists in the repository.
     *
     * @param name the email of the user to check
     * @return {@code true} if a {@link UserEntity} with the given email exists, {@code false} otherwise
     */
    Boolean existsByEmail(String name);
}
