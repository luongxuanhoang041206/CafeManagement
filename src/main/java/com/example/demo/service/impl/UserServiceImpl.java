package com.example.demo.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.AdminUserResponse;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.specification.UserSpecification;
import com.example.demo.entity.UserEntity;

import java.util.Optional; 

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

	//phan login
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = repo.findByNameOrEmail(username, username);
        if(user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                .username(userObj.getName())
                .password(userObj.getPassword())
                .roles("USER")
                .build();
        }
        else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
	}
}