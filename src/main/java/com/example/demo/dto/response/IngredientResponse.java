package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IngredientResponse {

    private Long id;
    private String name;
    private Long stock;
    private String unit;
    private LocalDateTime createdAt;
}
