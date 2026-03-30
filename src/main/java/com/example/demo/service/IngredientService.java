package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.CreateIngredientRequest;
import com.example.demo.dto.request.UpdateIngredientRequest;
import com.example.demo.dto.response.IngredientResponse;

public interface IngredientService {

    IngredientResponse create(CreateIngredientRequest request);

    IngredientResponse update(Long id, UpdateIngredientRequest request);

    void delete(Long id);

    List<IngredientResponse> getAll();

    IngredientResponse getById(Long id);
}
