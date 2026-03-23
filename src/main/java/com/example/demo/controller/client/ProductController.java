package com.example.demo.controller.client;

import com.example.demo.dto.response.AdminProductResponse;
import org.springframework.data.domain.Page;
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
    @GetMapping("/search")
    public Page<ProductResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        return service.searchForClient(name, minPrice, maxPrice, groupId, page, size);
    }
}