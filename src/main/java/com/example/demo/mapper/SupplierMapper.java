package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.SupplierResponse;
import com.example.demo.entity.SupplierEntity;

@Component
public class SupplierMapper {

    public SupplierResponse toResponse(SupplierEntity supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setPhone(supplier.getPhone());
        response.setEmail(supplier.getEmail());
        response.setAddress(supplier.getAddress());
        response.setNote(supplier.getNote());
        response.setCreatedAt(supplier.getCreatedAt());
        return response;
    }
}
