package com.example.demo.dto.response;

import java.util.Date;

import lombok.Data;
@Data
public class AdminUserResponse  {
	
	private String id;
	private String name;
	private String password;
	private String email;
	private boolean active;
	private Date created_at;
	
	public AdminUserResponse(String id, String name, String password, String email, boolean active, Date created_at) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.active = active;
		this.created_at = created_at;
	}
	
	
}