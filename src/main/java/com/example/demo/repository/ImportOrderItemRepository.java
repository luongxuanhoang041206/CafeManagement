package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ImportOrderItemEntity;

@Repository
public interface ImportOrderItemRepository extends JpaRepository<ImportOrderItemEntity, Long> {

    List<ImportOrderItemEntity> findByOrderId(Long orderId);
}
