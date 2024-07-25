package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.RefreshToken;
import com.aziz.lonelyapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String name);
    Boolean existsByToken(String name);

}
