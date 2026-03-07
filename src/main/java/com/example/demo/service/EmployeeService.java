package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.CreateEmployeeRequest;
import com.example.demo.dto.request.UpdateEmployeeRequest;
import com.example.demo.dto.response.AdminEmployeeResponse;
import com.example.demo.dto.response.AdminProductResponse;

public interface EmployeeService  {
	List<AdminEmployeeResponse> getAll();
	
	// pagination
//	Page<AdminProductResponse> search(
//			String name,
//			int page,
//			int size
//	);
	
	AdminEmployeeResponse create(CreateEmployeeRequest request);
	void deleteEmployee(String id);
	AdminEmployeeResponse update(UpdateEmployeeRequest request, String id);
	AdminEmployeeResponse detail(String id);
	Page<AdminEmployeeResponse> search(String name,Pageable pageable);
	
}