package com.example.demo.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateIngredientSupplierRequest {

    @NotNull(message = "Ingredient id must not be null")
    private Long ingredientId;

    @NotNull(message = "Supplier id must not be null")
    private Long supplierId;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}
