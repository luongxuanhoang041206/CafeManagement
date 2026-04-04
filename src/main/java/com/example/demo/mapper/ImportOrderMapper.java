package com.example.demo.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.ImportOrderItemResponse;
import com.example.demo.dto.response.ImportOrderResponse;
import com.example.demo.entity.ImportOrderEntity;
import com.example.demo.entity.ImportOrderItemEntity;

@Component
public class ImportOrderMapper {

    public ImportOrderResponse toResponse(ImportOrderEntity order, List<ImportOrderItemEntity> items) {
        ImportOrderResponse response = new ImportOrderResponse();
        response.setId(order.getId());
        response.setSupplierName(order.getSupplier().getName());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(items.stream().map(this::toItemResponse).toList());
        return response;
    }

    public ImportOrderItemResponse toItemResponse(ImportOrderItemEntity item) {
        ImportOrderItemResponse response = new ImportOrderItemResponse();
        response.setId(item.getId());
        response.setIngredientId(item.getIngredient().getId());
        response.setIngredientName(item.getIngredient().getName());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());
        response.setLineTotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return response;
    }
}
