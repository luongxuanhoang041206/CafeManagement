package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.response.AdminOrderResponse;

public interface OrderService {
	AdminOrderResponse create(CreateOrderRequest request);
	Page<AdminOrderResponse> search(
	        LocalDateTime fromDate,
	        LocalDateTime toDate,
	        Double minTotal,
	        Double maxTotal,
	        Long userId,
	        Long employeeId,
	        String orderSource,
	        String status,
	        Pageable pageable
	);
}