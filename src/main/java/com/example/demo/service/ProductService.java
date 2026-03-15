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
	//For client
	Page<ProductResponse> searchForClient(
			String name,
			Double minPrice,
			Double maxPrice,
			Integer groupId,
			int page,
			int size
	);
    List<AdminProductResponse> getAllForAdmin();
    //List<AdminProductResponse> search(String name, Double minPrice, Double maxPrice, Boolean actice, Interger group_id);
	List<AdminProductResponse> getAll();
	
	// pagination 
	Page<AdminProductResponse> search(
            String name,
            Double minPrice,
            Double maxPrice,
            Boolean active,
            Integer groupId,
            int page,
            int size
    );
	// end pagination
	AdminProductResponse create(CreateProductRequest request);
	AdminProductResponse changeStatus(Long id);
	void deleteProduct(Long id);
	AdminProductResponse update(UpdateProductRequest request, Long id);
	AdminProductResponse detail(Long id);
	Page<AdminProductResponse> search(String name, Double minPrice, Double maxPrice, Boolean active, Integer groupId,
			Pageable pageable);

}