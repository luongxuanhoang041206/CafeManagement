package com.example.demo.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.request.CreateIngredientSupplierRequest;
import com.example.demo.dto.request.UpdateIngredientSupplierRequest;
import com.example.demo.dto.response.IngredientSupplierResponse;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.IngredientSupplierEntity;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.mapper.IngredientSupplierMapper;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.IngredientSupplierRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.IngredientSupplierService;

@Service
public class IngredientSupplierServiceImpl implements IngredientSupplierService {

    private final IngredientSupplierRepository ingredientSupplierRepository;
    private final IngredientRepository ingredientRepository;
    private final SupplierRepository supplierRepository;
    private final IngredientSupplierMapper ingredientSupplierMapper;

    public IngredientSupplierServiceImpl(
            IngredientSupplierRepository ingredientSupplierRepository,
            IngredientRepository ingredientRepository,
            SupplierRepository supplierRepository,
            IngredientSupplierMapper ingredientSupplierMapper
    ) {
        this.ingredientSupplierRepository = ingredientSupplierRepository;
        this.ingredientRepository = ingredientRepository;
        this.supplierRepository = supplierRepository;
        this.ingredientSupplierMapper = ingredientSupplierMapper;
    }

    @Transactional
    @Override
    public IngredientSupplierResponse create(CreateIngredientSupplierRequest request) {
        IngredientEntity ingredient = ingredientRepository.findById(request.getIngredientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found"));
        SupplierEntity supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        IngredientSupplierEntity ingredientSupplier = new IngredientSupplierEntity();
        ingredientSupplier.setIngredient(ingredient);
        ingredientSupplier.setSupplier(supplier);
        ingredientSupplier.setPrice(request.getPrice());

        IngredientSupplierEntity savedIngredientSupplier = ingredientSupplierRepository.save(ingredientSupplier);
        return ingredientSupplierMapper.toResponse(savedIngredientSupplier);
    }

    @Transactional(readOnly = true)
    @Override
    public List<IngredientSupplierResponse> getAll() {
        return ingredientSupplierRepository.findAll()
                .stream()
                .map(ingredientSupplierMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public IngredientSupplierResponse getById(Long id) {
        IngredientSupplierEntity ingredientSupplier = ingredientSupplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient supplier not found"));

        return ingredientSupplierMapper.toResponse(ingredientSupplier);
    }

    @Transactional
    @Override
    public IngredientSupplierResponse update(Long id, UpdateIngredientSupplierRequest request) {
        IngredientSupplierEntity ingredientSupplier = ingredientSupplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient supplier not found"));
        IngredientEntity ingredient = ingredientRepository.findById(request.getIngredientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found"));
        SupplierEntity supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        ingredientSupplier.setIngredient(ingredient);
        ingredientSupplier.setSupplier(supplier);
        ingredientSupplier.setPrice(request.getPrice());

        IngredientSupplierEntity updatedIngredientSupplier = ingredientSupplierRepository.save(ingredientSupplier);
        return ingredientSupplierMapper.toResponse(updatedIngredientSupplier);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        IngredientSupplierEntity ingredientSupplier = ingredientSupplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient supplier not found"));

        ingredientSupplierRepository.delete(ingredientSupplier);
    }
}
