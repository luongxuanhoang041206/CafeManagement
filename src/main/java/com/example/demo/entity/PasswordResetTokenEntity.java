package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class PasswordResetTokenEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resetPWId;
	private Long userId;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenStatus status;
    private LocalDateTime createAt;
    
    public Long getResetPWId() {
        return resetPWId;
    }
    public Long getUserId() {
        return userId;
    }
    public String getToken() {
        return token;
    }
    public TokenStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreateAt() {
        return createAt;
    }
    public void setResetPWId(Long resetPWId) {
        this.resetPWId = resetPWId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setStatus(TokenStatus status) {
        this.status = status;
    }
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    
    
    
}
