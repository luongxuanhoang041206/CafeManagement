package com.example.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateIngredientRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Long stock;

    @NotBlank(message = "Unit must not be blank")
    private String unit;
}
