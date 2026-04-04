package com.example.demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.ImportOrderStatus;

import lombok.Data;

@Data
public class ImportOrderResponse {

    private Long id;
    private String supplierName;
    private BigDecimal totalPrice;
    private ImportOrderStatus status;
    private LocalDateTime createdAt;
    private List<ImportOrderItemResponse> items;
}
