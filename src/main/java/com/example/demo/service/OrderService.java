package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.ClientCreateOrderRequest;
import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.response.AdminOrderResponse;
import com.example.demo.dto.response.ClientOrderResponse;
import com.example.demo.dto.response.OrderResponse;

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
	AdminOrderResponse detail(Long id);
	AdminOrderResponse updateStatus(Long id, String status);
	ClientOrderResponse clientCreate(ClientCreateOrderRequest request);
}