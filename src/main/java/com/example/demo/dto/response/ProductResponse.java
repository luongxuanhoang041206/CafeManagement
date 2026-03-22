package com.example.demo.dto.response;

import lombok.Data;

@Data
public class ProductResponse {

	private Long id;
    private String name;
    private double price;

    public ProductResponse(Long id,String name, double price) {
    	this.id = id;
        this.name = name;
        this.price = price;
    }
}