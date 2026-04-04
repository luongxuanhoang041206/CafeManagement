package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.CreateIngredientSupplierRequest;
import com.example.demo.dto.request.UpdateIngredientSupplierRequest;
import com.example.demo.dto.response.IngredientSupplierResponse;

public interface IngredientSupplierService {

    IngredientSupplierResponse create(CreateIngredientSupplierRequest request);

    List<IngredientSupplierResponse> getAll();

    IngredientSupplierResponse getById(Long id);

    IngredientSupplierResponse update(Long id, UpdateIngredientSupplierRequest request);

    void delete(Long id);
}
