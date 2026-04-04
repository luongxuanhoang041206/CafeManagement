package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.PasswordResetDTO;
import com.example.demo.dto.request.TokenRequestDTO;

@Service
public interface PasswordResetService {
    public String generateResetToken(TokenRequestDTO tokenRequestDTO);
    public String resetPassword(PasswordResetDTO passwordResetDTO);
}
