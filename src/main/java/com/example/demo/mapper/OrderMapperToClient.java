package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.ClientOrderResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.OrderEntity;

@Component
public class OrderMapperToClient  {
	public ClientOrderResponse toClient(OrderEntity o) {
		return new ClientOrderResponse(
				o.getId(),
				o.getOrderSource(),
				o.getTableId(),
				o.getUserId(),
				o.getEmployeeId(),
				o.getStatus(),
				o.getTotalAmount(),
				o.getMethodPayment(),
				o.getAddress(),
				o.getCreatedAt()
		);
	}
}