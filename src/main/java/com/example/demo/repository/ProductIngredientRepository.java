package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.ProductIngredientEntity;
public interface ProductIngredientRepository extends JpaRepository<ProductIngredientEntity, Long> {
	
	List<ProductIngredientEntity> findByProductId(Long productId);
}