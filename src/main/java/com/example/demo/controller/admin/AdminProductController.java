package com.example.demo.controller.admin;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.AdminProductResponse;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService service;

    public AdminProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<AdminProductResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {

        return service.search(name, minPrice, maxPrice, active, groupId, page, size).getContent();
    }
    @PostMapping
    public AdminProductResponse create(@RequestBody CreateProductRequest request) {
        return service.create(request);
    }
}