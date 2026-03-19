package com.example.demo.controller.client;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderController  {
	private final OrderService service;
	public OrderController(OrderService service) {
		this.service = service;
	}

	@PostMapping
	public OrderResponse create(@RequestBody CreateOrderRequest request) {
		return service.create(request);
	}
}