package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.IngredientSupplierResponse;
import com.example.demo.entity.IngredientSupplierEntity;

@Component
public class IngredientSupplierMapper {

    public IngredientSupplierResponse toResponse(IngredientSupplierEntity ingredientSupplier) {
        IngredientSupplierResponse response = new IngredientSupplierResponse();
        response.setId(ingredientSupplier.getId());
        response.setIngredientId(ingredientSupplier.getIngredient().getId());
        response.setIngredientName(ingredientSupplier.getIngredient().getName());
        response.setSupplierId(ingredientSupplier.getSupplier().getId());
        response.setSupplierName(ingredientSupplier.getSupplier().getName());
        response.setPrice(ingredientSupplier.getPrice());
        return response;
    }
}
