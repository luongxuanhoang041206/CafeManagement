package com.example.demo.controller.client;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.demo.dto.response.ProductResponse;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // Client chỉ xem sản phẩm active
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllForClient();
    }
}