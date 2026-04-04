package com.example.demo.dto.request;

public record PasswordResetRequest(Long userId, String newPassword, String token) {

}