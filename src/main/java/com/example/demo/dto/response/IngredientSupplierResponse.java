package com.example.demo.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class IngredientSupplierResponse {

    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private Long supplierId;
    private String supplierName;
    private BigDecimal price;
}
