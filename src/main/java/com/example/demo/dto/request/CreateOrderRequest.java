package com.example.demo.dto.request;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data

public class CreateOrderRequest  {
	private String orderSource;
	private Long tableId;
	private Long userId;
	private Long employeeId;
	private String status;
	private Long totalAmount;
	private Date created_at;
	private List<CreateOrderItemRequest> items;
    private String paymentMethod;
}