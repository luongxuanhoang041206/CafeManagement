package com.example.demo.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.request.UpdateProductRequest;
import com.example.demo.dto.response.AdminProductResponse;
import com.example.demo.service.ProductService;
import org.springframework.data.domain.Pageable;
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService service;

    public AdminProductController(ProductService service) {
        this.service = service;
    }
    // Lay danh sach san pham + pagination + search
    @GetMapping
    public Page<AdminProductResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

    	Sort sort = direction.equalsIgnoreCase("asc")
    			?Sort.by(sortBy).ascending()
    			: Sort.by(sortBy).descending();
    	
    	Pageable pageable = PageRequest.of(page, size, sort);
        return service.search(name, minPrice, maxPrice, active, groupId, pageable);
    }
    // tao moi san pham
    @PostMapping
    public AdminProductResponse create(@RequestBody CreateProductRequest request) {
        return service.create(request);
    }
    // sua trang thai san pham
    @PatchMapping("/{id}/status")
    public AdminProductResponse changeStatus(@PathVariable Long id) {
        return service.changeStatus(id);
    }
    // Xoa san pham
    @DeleteMapping("/{id}/delete")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }
    // Cap nhat san pham
    @PatchMapping("/{id}")
    public AdminProductResponse update(@RequestBody UpdateProductRequest request,
    		@PathVariable Long id) {
    	return service.update(request, id);
    }
    // Xem chi tiet san pham
    @GetMapping("/{id}")
    public AdminProductResponse detail(@PathVariable Long id) {
    	return service.detail(id);
    }
}