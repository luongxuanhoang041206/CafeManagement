package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.OrderEntity;

@Component
public class OrderMapperToClient  {
	public OrderResponse toClient(OrderEntity o) {
		return new OrderResponse(
				o.getId(),
				o.getOrderSource(),
				o.getTableId(),
				o.getUserId(),
				o.getEmployeeId(),
				o.getStatus(),
				o.getTotalAmount(),
				o.getCreatedAt()
		);
	}
}