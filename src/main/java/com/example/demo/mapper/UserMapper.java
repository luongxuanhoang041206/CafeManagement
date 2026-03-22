package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.AdminUserResponse;
import com.example.demo.entity.UserEntity;

@Component
public class UserMapper  {
	public AdminUserResponse toAdmin(UserEntity u) {
		return new AdminUserResponse(
				u.getId(),
				u.getName(),
				u.getPassword(),
				u.getEmail(),
				u.isActive(),
				u.getCreatedAt(),
				u.getRole()
		);
	}
}