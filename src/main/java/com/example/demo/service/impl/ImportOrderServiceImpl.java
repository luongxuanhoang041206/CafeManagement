package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.request.CreateImportOrderItemRequest;
import com.example.demo.dto.request.CreateImportOrderRequest;
import com.example.demo.dto.response.ImportOrderResponse;
import com.example.demo.entity.ImportOrderEntity;
import com.example.demo.entity.ImportOrderItemEntity;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.mapper.ImportOrderMapper;
import com.example.demo.repository.ImportOrderItemRepository;
import com.example.demo.repository.ImportOrderRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.ImportOrderService;

@Service
public class ImportOrderServiceImpl implements ImportOrderService {

    private final ImportOrderRepository importOrderRepository;
    private final ImportOrderItemRepository importOrderItemRepository;
    private final SupplierRepository supplierRepository;
    private final IngredientRepository ingredientRepository;
    private final ImportOrderMapper importOrderMapper;

    public ImportOrderServiceImpl(
            ImportOrderRepository importOrderRepository,
            ImportOrderItemRepository importOrderItemRepository,
            SupplierRepository supplierRepository,
            IngredientRepository ingredientRepository,
            ImportOrderMapper importOrderMapper
    ) {
        this.importOrderRepository = importOrderRepository;
        this.importOrderItemRepository = importOrderItemRepository;
        this.supplierRepository = supplierRepository;
        this.ingredientRepository = ingredientRepository;
        this.importOrderMapper = importOrderMapper;
    }

    @Transactional
    @Override
    public ImportOrderResponse create(CreateImportOrderRequest request) {
        SupplierEntity supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        ImportOrderEntity order = new ImportOrderEntity();
        order.setSupplier(supplier);
        order.setStatus(request.getStatus());
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(BigDecimal.ZERO);

        ImportOrderEntity savedOrder = importOrderRepository.save(order);

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<ImportOrderItemEntity> orderItems = new ArrayList<>();

        for (CreateImportOrderItemRequest itemRequest : request.getItems()) {
            IngredientEntity ingredient = ingredientRepository.findById(itemRequest.getIngredientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found"));

            ImportOrderItemEntity orderItem = new ImportOrderItemEntity();
            orderItem.setOrder(savedOrder);
            orderItem.setIngredient(ingredient);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(itemRequest.getPrice());
            orderItems.add(orderItem);

            totalPrice = totalPrice.add(itemRequest.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            long currentStock = ingredient.getStock() == null ? 0L : ingredient.getStock();
            ingredient.setStock(currentStock + itemRequest.getQuantity());
            ingredientRepository.save(ingredient);
        }

        List<ImportOrderItemEntity> savedItems = importOrderItemRepository.saveAll(orderItems);
        savedOrder.setTotalPrice(totalPrice);
        ImportOrderEntity updatedOrder = importOrderRepository.save(savedOrder);

        return importOrderMapper.toResponse(updatedOrder, savedItems);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ImportOrderResponse> getAll() {
        return importOrderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(order -> importOrderMapper.toResponse(order, importOrderItemRepository.findByOrderId(order.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ImportOrderResponse getById(Long id) {
        ImportOrderEntity order = importOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Import order not found"));

        return importOrderMapper.toResponse(order, importOrderItemRepository.findByOrderId(order.getId()));
    }
}
