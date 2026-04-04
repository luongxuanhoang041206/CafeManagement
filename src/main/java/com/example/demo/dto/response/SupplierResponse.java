package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SupplierResponse {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String note;
    private LocalDateTime createdAt;
}
