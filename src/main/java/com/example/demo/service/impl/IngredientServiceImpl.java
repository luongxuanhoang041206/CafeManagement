package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.CreateIngredientRequest;
import com.example.demo.dto.request.UpdateIngredientRequest;
import com.example.demo.dto.response.IngredientResponse;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientResponse create(CreateIngredientRequest request) {
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName(request.getName());
        ingredient.setStock(request.getStock());
        ingredient.setUnit(request.getUnit());
        ingredient.setCreatedAt(LocalDateTime.now());

        IngredientEntity savedIngredient = ingredientRepository.save(ingredient);
        return toResponse(savedIngredient);
    }

    @Override
    public IngredientResponse update(Long id, UpdateIngredientRequest request) {
        IngredientEntity ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        ingredient.setName(request.getName());
        ingredient.setStock(request.getStock());
        ingredient.setUnit(request.getUnit());

        IngredientEntity updatedIngredient = ingredientRepository.save(ingredient);
        return toResponse(updatedIngredient);
    }

    @Override
    public void delete(Long id) {
        IngredientEntity ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        ingredientRepository.delete(ingredient);
    }

    @Override
    public List<IngredientResponse> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public IngredientResponse getById(Long id) {
        IngredientEntity ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        return toResponse(ingredient);
    }

    private IngredientResponse toResponse(IngredientEntity ingredient) {
        IngredientResponse response = new IngredientResponse();
        response.setId(ingredient.getId());
        response.setName(ingredient.getName());
        response.setStock(ingredient.getStock());
        response.setUnit(ingredient.getUnit());
        response.setCreatedAt(ingredient.getCreatedAt());
        return response;
    }
}
