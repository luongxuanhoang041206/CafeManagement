package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.AdminOrderResponse;
import com.example.demo.entity.OrderEntity;

@Component
public class OrderMapperToAdmin  {
	public AdminOrderResponse toAdmin(OrderEntity o) {
		return new AdminOrderResponse(
				o.getId(),
				o.getOrderSource(),
				o.getTableId(),
				o.getUserId(),
				o.getEmployeeId(),
				o.getStatus(),
				o.getTotalAmount(),
				o.getMethodPayment(),
				o.getCreatedAt()
		);
	}
}