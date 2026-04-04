package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSupplierRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Phone must not be blank")
    private String phone;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Address must not be blank")
    private String address;

    private String note;
}
