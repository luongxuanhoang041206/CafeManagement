package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.EmployeeEntity;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String>,
											JpaSpecificationExecutor<EmployeeEntity>
{
	List<EmployeeEntity> findByNameContainingIgnoreCase(String name);
}