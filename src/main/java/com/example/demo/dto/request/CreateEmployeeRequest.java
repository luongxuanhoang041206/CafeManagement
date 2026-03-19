package com.example.demo.dto.request;

import com.example.demo.entity.Role;

import lombok.Data;

@Data
public class CreateEmployeeRequest  {
	private String id;
	private String name;
	private String position;
	private String phone;
	//private String avatar;
	private String username;
	private Role role;
	private String password;
	private String salary;
}