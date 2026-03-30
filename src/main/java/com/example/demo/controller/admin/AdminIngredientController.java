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

import com.example.demo.dto.request.CreateIngredientRequest;
import com.example.demo.dto.request.UpdateIngredientRequest;
import com.example.demo.dto.response.IngredientResponse;
import com.example.demo.service.IngredientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/ingredients")
public class AdminIngredientController {

    private final IngredientService ingredientService;

    public AdminIngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public IngredientResponse create(@Valid @RequestBody CreateIngredientRequest request) {
        return ingredientService.create(request);
    }

    @GetMapping
    public List<IngredientResponse> getAll() {
        return ingredientService.getAll();
    }

    @GetMapping("/{id}")
    public IngredientResponse getById(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @PutMapping("/{id}")
    public IngredientResponse update(@PathVariable Long id, @Valid @RequestBody UpdateIngredientRequest request) {
        return ingredientService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ingredientService.delete(id);
    }
}
