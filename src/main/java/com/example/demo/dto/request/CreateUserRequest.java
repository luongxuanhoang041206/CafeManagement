package com.example.demo.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class CreateUserRequest  {
	private Long id;
	private String name;
	private String password;
	private String email;
	private boolean active;
	private Date created_at;
	private String role;
	public CreateUserRequest(Long id, String name, String password, String email, boolean active, Date created_at, String role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.active = active;
		this.created_at = created_at;
		this.role = role;
	}
	
	
}