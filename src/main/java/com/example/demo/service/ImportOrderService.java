package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.CreateImportOrderRequest;
import com.example.demo.dto.response.ImportOrderResponse;

public interface ImportOrderService {

    ImportOrderResponse create(CreateImportOrderRequest request);

    List<ImportOrderResponse> getAll();

    ImportOrderResponse getById(Long id);
}
