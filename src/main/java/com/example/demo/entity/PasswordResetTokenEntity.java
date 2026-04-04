package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
@Table(name = "password_reset_tokens")
@Entity
@Data
public class PasswordResetTokenEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reset_pwid")
	private Long resetPWId;
	private Long userId;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expireAt;
}