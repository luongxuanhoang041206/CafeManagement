package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.AdminProductResponse;
import com.example.demo.dto.response.ProductResponse;

public interface ProductService {

    List<ProductResponse> getAllForClient();
    List<AdminProductResponse> getAllForAdmin();
    //List<AdminProductResponse> search(String name, Double minPrice, Double maxPrice, Boolean actice, String group_id);
	List<AdminProductResponse> getAll();
	
	// pagination 
	Page<AdminProductResponse> search(
            String name,
            Double minPrice,
            Double maxPrice,
            Boolean active,
            String groupId,
            int page,
            int size
    );
	// end pagination
	
	AdminProductResponse create(CreateProductRequest request);
	AdminProductResponse changeStatus(String id);
	
}