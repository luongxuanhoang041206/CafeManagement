package com.example.demo.dto.request;

import lombok.Data;

@Data
public class CreateEmployeeRequest  {
	private String id;
	private String name;
	private String position;
	private String phone;
	//private String avatar;
	private String salary;
}