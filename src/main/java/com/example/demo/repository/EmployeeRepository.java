package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>,
											JpaSpecificationExecutor<EmployeeEntity>
{
	List<EmployeeEntity> findByNameContainingIgnoreCase(String name);
    Optional<EmployeeEntity> findByUsername(String username);
}