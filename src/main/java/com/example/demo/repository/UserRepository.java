package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.UserEntity;

//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface UserRepository extends JpaRepository<UserEntity, Long>,
											JpaSpecificationExecutor<UserEntity>
{
	List<UserEntity> findByNameContainingIgnoreCase(String name);
}