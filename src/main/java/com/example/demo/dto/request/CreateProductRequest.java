package com.example.demo.dto.request;

import lombok.Data;

@Data
public class CreateProductRequest {
	//private String id;
    private String name;
    private Double price;
    private Boolean active;
    private Long groupId;
    private String imageUrl;
}