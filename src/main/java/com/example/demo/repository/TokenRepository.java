package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PasswordResetTokenEntity;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.TokenStatus;


public interface TokenRepository extends JpaRepository < PasswordResetTokenEntity, Long> {
    Optional<PasswordResetTokenEntity> findByTokenAndStatus(String token, TokenStatus status);
    List<PasswordResetTokenEntity> findAllByUserIdAndStatus(Long userId, TokenStatus status);
}
