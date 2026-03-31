package com.example.demo.dto.response;

import lombok.Data;

@Data
public class AdminProductResponse {

    private Long id;
    private String name;
    private double price;
    private boolean active;
    private Long groupId;
    private String imageUrl;
    private boolean available;

    public AdminProductResponse(Long id, String name,
                                double price, boolean active, Long groupId, String imageUrl, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.groupId = groupId;
        this.imageUrl = imageUrl;
        this.available = available;
    }
}