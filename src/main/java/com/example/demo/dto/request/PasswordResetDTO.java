package com.example.demo.dto.request;

public record PasswordResetDTO(Long userId, String newPassword, String token) {

}
