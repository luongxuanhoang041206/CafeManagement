package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
@Data
public class AdminUserResponse  {
	
	private Long id;
	private String username;
	private String password;
	private String email;
	private boolean active;
	private LocalDateTime created_at;
	private String role;
	public AdminUserResponse(Long id, String username, String password, String email, boolean active, LocalDateTime created_at, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.active = active;
		this.created_at = created_at;
		this.role = role;
	}
}