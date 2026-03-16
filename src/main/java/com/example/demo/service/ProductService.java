package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.request.UpdateProductRequest;
import com.example.demo.dto.response.AdminProductResponse;
import com.example.demo.dto.response.ProductResponse;

public interface ProductService {

    List<ProductResponse> getAllForClient();
    List<AdminProductResponse> getAllForAdmin();
	List<AdminProductResponse> getAll();
	AdminProductResponse create(CreateProductRequest request);
	AdminProductResponse changeStatus(Long id);
	void deleteProduct(Long id);
	AdminProductResponse update(UpdateProductRequest request, Long id);
	AdminProductResponse detail(Long id);
	Page<AdminProductResponse> search(String name, Double minPrice, Double maxPrice, Boolean active, Long groupId,
			Pageable pageable);
	Page<ProductResponse> searchForClient(String name, Double minPrice, Double maxPrice, Long groupId, int page,
			int size);
}