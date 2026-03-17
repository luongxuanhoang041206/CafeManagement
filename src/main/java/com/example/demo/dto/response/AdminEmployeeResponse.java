package com.example.demo.dto.response;

import lombok.Data;

@Data
public class AdminEmployeeResponse  {
	
	public AdminEmployeeResponse(Long long1, String name, String phone, String position, String salary) {
		this.id = long1;
		this.name = name;
		this.phone = phone;
		this.position = position;
		this.salary = salary;
	}
	private Long id;
	//private String avatar;
	private String name;
	private String phone;
	private String position;
	private String salary;
}