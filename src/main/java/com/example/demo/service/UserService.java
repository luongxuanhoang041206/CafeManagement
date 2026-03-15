package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.dto.response.AdminUserResponse;


public interface UserService extends UserDetailsService  {
	Page<AdminUserResponse> search(String name, Pageable pageable);
	void changeActive(Long id);
	void deleteUser(Long id);
}