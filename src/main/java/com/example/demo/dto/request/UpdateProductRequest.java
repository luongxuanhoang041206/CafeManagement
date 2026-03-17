package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UpdateProductRequest {
	//private String id;
    private String name;
    private Double price;
    private Boolean active;
    private Long groupId;
}