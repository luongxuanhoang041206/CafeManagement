package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.CreateEmployeeRequest;
import com.example.demo.dto.request.UpdateEmployeeRequest;
import com.example.demo.dto.response.AdminEmployeeResponse;

public interface EmployeeService  {
	List<AdminEmployeeResponse> getAll();
	
	// pagination
//	Page<AdminProductResponse> search(
//			String name,
//			int page,
//			int size
//	);
	
	AdminEmployeeResponse create(CreateEmployeeRequest request);
	void deleteEmployee(Long id);
//	AdminEmployeeResponse update1(UpdateEmployeeRequest request, Long id);
	AdminEmployeeResponse detail(Long id);
	Page<AdminEmployeeResponse> search(String name,Pageable pageable);

	AdminEmployeeResponse update(UpdateEmployeeRequest request, Long id);
	
}