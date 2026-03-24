package com.example.demo.dto.response;

import lombok.Data;

@Data
public class ProductResponse {

	private Long id;
    private String name;
    private double price;
    private String imageUrl;

    public ProductResponse(Long id,String name, double price, String imageUrl) {
    	this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}