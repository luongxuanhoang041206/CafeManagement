package com.example.demo.dto.response;

import lombok.Data;

@Data
public class AdminEmployeeResponse  {
	
	public AdminEmployeeResponse(String id, String name, String phone, String position, String salary) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.position = position;
		this.salary = salary;
	}
	private String id;
	//private String avatar;
	private String name;
	private String phone;
	private String position;
	private String salary;
}