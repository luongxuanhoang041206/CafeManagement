package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.CreateSupplierRequest;
import com.example.demo.dto.request.UpdateSupplierRequest;
import com.example.demo.dto.response.SupplierResponse;

public interface SupplierService {

    SupplierResponse create(CreateSupplierRequest request);

    List<SupplierResponse> getAll();

    SupplierResponse update(Long id, UpdateSupplierRequest request);

    void delete(Long id);
}
