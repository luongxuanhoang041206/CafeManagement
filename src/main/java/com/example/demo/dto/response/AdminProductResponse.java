package com.example.demo.dto.response;

import lombok.Data;

@Data
public class AdminProductResponse {

    private Long id;
    private String name;
    private double price;
    private boolean active;
    private String groupId;

    public AdminProductResponse(Long id, String name,
                                double price, boolean active, String groupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.groupId = groupId;
    }
}