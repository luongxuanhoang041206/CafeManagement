package com.example.demo.dto.response;

import com.example.demo.entity.UserEntity;

import lombok.Data;

@Data
public class LoginResponse {
    private Long userId;
    private String username;
    private String role;
}