package com.example.demo.specification;

import com.example.demo.entity.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpecification {

    public static Specification<OrderEntity> fromDate(LocalDateTime fromDate) {
        return (root, query, cb) -> {
            if (fromDate == null) return null;
            return cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate);
        };
    }

    public static Specification<OrderEntity> toDate(LocalDateTime toDate) {
        return (root, query, cb) -> {
            if (toDate == null) return null;
            return cb.lessThanOrEqualTo(root.get("createdAt"), toDate);
        };
    }

    public static Specification<OrderEntity> minTotal(Double minTotal) {
        return (root, query, cb) -> {
            if (minTotal == null) return null;
            return cb.greaterThanOrEqualTo(root.get("totalAmount"), minTotal);
        };
    }

    public static Specification<OrderEntity> maxTotal(Double maxTotal) {
        return (root, query, cb) -> {
            if (maxTotal == null) return null;
            return cb.lessThanOrEqualTo(root.get("totalAmount"), maxTotal);
        };
    }

    public static Specification<OrderEntity> userId(Long userId) {
        return (root, query, cb) -> {
            if (userId == null) return null;
            return cb.equal(root.get("user").get("id"), userId);
        };
    }

    public static Specification<OrderEntity> employeeId(Long employeeId) {
        return (root, query, cb) -> {
            if (employeeId == null) return null;
            return cb.equal(root.get("employee").get("id"), employeeId);
        };
    }

    public static Specification<OrderEntity> orderSource(String orderSource) {
        return (root, query, cb) -> {
            if (orderSource == null) return null;
            return cb.equal(root.get("orderSource"), orderSource);
        };
    }

    public static Specification<OrderEntity> status(String status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }
}