package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
	@Query("SELECT SUM(p.amount) FROM PaymentEntity p WHERE p.status = 'PAID'")
	Long getTotalRevenue();
}