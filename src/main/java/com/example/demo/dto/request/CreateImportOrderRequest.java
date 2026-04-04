package com.example.demo.dto.request;

import java.util.List;

import com.example.demo.entity.ImportOrderStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateImportOrderRequest {

    @NotNull(message = "Supplier id must not be null")
    private Long supplierId;
    
    private ImportOrderStatus status;

    @Valid
    @NotEmpty(message = "Import order items must not be empty")
    private List<CreateImportOrderItemRequest> items;
}
