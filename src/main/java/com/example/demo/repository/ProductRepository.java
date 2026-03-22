package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.ProductEntity;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>
											,JpaSpecificationExecutor<ProductEntity>
										
{
	List<ProductEntity> findByActiveTrue();
	
	//List<ProductEntity> findByNameContainingIgnoreCase(String name);
	
}