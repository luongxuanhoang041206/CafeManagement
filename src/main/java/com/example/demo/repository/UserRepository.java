package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.UserEntity;
import java.util.Optional;

//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface UserRepository extends JpaRepository<UserEntity, Long>,
											JpaSpecificationExecutor<UserEntity>
{
	List<UserEntity> findByNameContainingIgnoreCase(String name);
	
	//phuc vu login
	Optional<UserEntity> findByNameOrEmail(String name, String email);
	Optional<UserEntity> findByUsername(String name);
	
	//phuc vu signup validation
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}