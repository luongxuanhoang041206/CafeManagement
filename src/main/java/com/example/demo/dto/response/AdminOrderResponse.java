package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminOrderResponse  {
	private Long id;
	private String orderSource;
	private Long tableId;
	private String tableLabel;
	private Long userId;
	private Long employeeId;
	private String status;
	private Long totalAmount;
	private String methodPayment;
	private String address;
	private LocalDateTime created_at;
	private UserSummary customer;
	private EmployeeSummary employee;
	private PaymentSummary payment;
	private List<OrderItemDetail> items;
	
	public AdminOrderResponse(Long id, String orderSource, Long tableId, Long userId, Long employeeId, String status,
			Long totalAmount,String methodPayment, LocalDateTime localDateTime) {
		this.id = id;
		this.orderSource = orderSource;
		this.tableId = tableId;
		this.userId = userId;
		this.employeeId = employeeId;
		this.status = status;
		this.totalAmount = totalAmount;
		this.methodPayment = methodPayment;
		this.created_at = localDateTime;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserSummary {
		private Long id;
		private String name;
		private String username;
		private String email;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class EmployeeSummary {
		private Long id;
		private String name;
		private String position;
		private String phone;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PaymentSummary {
		private Long id;
		private String method;
		private Long amount;
		private String status;
		private LocalDateTime paidAt;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OrderItemDetail {
		private Long id;
		private Long productId;
		private String productName;
		private Integer quantity;
		private Integer price;
		private Long lineTotal;
	}
}
