package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CreateIngredientSupplierRequest;
import com.example.demo.dto.request.UpdateIngredientSupplierRequest;
import com.example.demo.dto.response.IngredientSupplierResponse;
import com.example.demo.service.IngredientSupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/ingredient-suppliers")
public class IngredientSupplierController {

    private final IngredientSupplierService ingredientSupplierService;

    public IngredientSupplierController(IngredientSupplierService ingredientSupplierService) {
        this.ingredientSupplierService = ingredientSupplierService;
    }

    @PostMapping
    public IngredientSupplierResponse create(@Valid @RequestBody CreateIngredientSupplierRequest request) {
        return ingredientSupplierService.create(request);
    }

    @GetMapping
    public List<IngredientSupplierResponse> getAll() {
        return ingredientSupplierService.getAll();
    }

    @GetMapping("/{id}")
    public IngredientSupplierResponse getById(@PathVariable Long id) {
        return ingredientSupplierService.getById(id);
    }

    @PutMapping("/{id}")
    public IngredientSupplierResponse update(@PathVariable Long id,
            @Valid @RequestBody UpdateIngredientSupplierRequest request) {
        return ingredientSupplierService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ingredientSupplierService.delete(id);
    }
}
