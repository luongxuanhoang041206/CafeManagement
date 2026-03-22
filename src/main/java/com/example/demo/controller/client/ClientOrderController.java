package com.example.demo.controller.client;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ClientCreateOrderRequest;
import com.example.demo.dto.response.ClientOrderResponse;
import com.example.demo.service.OrderService;

@CrossOrigin(origins = "https://fe-cafe-management-qtup6nb42-luongxuanhoang041206s-projects.vercel.app", allowCredentials = "true")
@RestController
@RequestMapping("/orders")
public class ClientOrderController  {
	private final OrderService service;

	public ClientOrderController(OrderService service) {
		this.service = service;
	}
	
	@PostMapping
	public ClientOrderResponse clientCreate(@RequestBody ClientCreateOrderRequest request) {
		return service.clientCreate(request);
	}
	
}