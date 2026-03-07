package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UpdateEmployeeRequest  {
	private String id;
	private String name;
	//private String avatar;
	private String salary;
}