package com.example.demo.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.AdminUserResponse;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.specification.UserSpecification;
import com.example.demo.entity.UserEntity;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository repo;
	private final UserMapper mapper;
	
	public UserServiceImpl(UserRepository repo,
							UserMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	@Override
	public Page<AdminUserResponse> search(
			String name,
			Pageable pageable
		) {
		Specification<UserEntity> spec = UserSpecification.filter(name);
		Page<UserEntity> userPage = repo.findAll(spec, pageable);
		return userPage.map(mapper::toAdmin);
	}
	@Override
	public void changeActive(Long id) {
		UserEntity user = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));
	}
	@Override
	public void deleteUser(Long id) {
		UserEntity user = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));
		
		repo.deleteById(id);
	}
}