package com.example.demo.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ImportOrderItemResponse {

    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal lineTotal;
}
