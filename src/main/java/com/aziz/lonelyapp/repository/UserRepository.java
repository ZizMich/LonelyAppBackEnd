package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);


    Boolean existsByEmail(String name);
}
