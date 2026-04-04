package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
	List<OrderItemEntity> findByOrderId(Long orderId);
}
