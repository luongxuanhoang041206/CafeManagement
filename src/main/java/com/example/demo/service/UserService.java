package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.response.AdminUserResponse;


public interface UserService  {
	Page<AdminUserResponse> search(String username, Pageable pageable);
	void changeActive(Long id);
	void deleteUser(Long id);
}