package com.example.demo.dto.request;

import lombok.Data;

@Data
public class CreateOrderItemRequest  {
	private Long productId;
    private Integer quantity;
    private Long price;
}