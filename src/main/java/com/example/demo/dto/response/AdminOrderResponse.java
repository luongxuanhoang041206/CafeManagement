package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class AdminOrderResponse  {
	private Long id;
	private String orderSource;
	private Long tableId;
	private Long userId;
	private Long employeeId;
	private String status;
	private Long totalAmount;
	private LocalDateTime created_at;
	
	public AdminOrderResponse(Long id, String orderSource, Long tableId, Long userId, Long employeeId, String status,
			Long totalAmount, LocalDateTime localDateTime) {
		this.id = id;
		this.orderSource = orderSource;
		this.tableId = tableId;
		this.userId = userId;
		this.employeeId = employeeId;
		this.status = status;
		this.totalAmount = totalAmount;
		this.created_at = localDateTime;
	}
}