package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.PasswordResetRequest;
import com.example.demo.dto.request.TokenRequest;

@Service
public interface PasswordResetService {
    public String generateResetToken(TokenRequest tokenRequestDTO);
    public String resetPassword(PasswordResetRequest passwordResetDTO);
    public boolean isResetTokenValid(String token);
}
