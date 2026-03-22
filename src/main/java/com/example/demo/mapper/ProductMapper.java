package com.example.demo.mapper;

import org.springframework.stereotype.Component;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.response.AdminProductResponse;
import com.example.demo.entity.ProductEntity;

@Component
public class ProductMapper {

    public ProductResponse toClient(ProductEntity p) {
        return new ProductResponse(
        		p.getId(),
                p.getName(),
                p.getPrice()
        );
    }

    public AdminProductResponse toAdmin(ProductEntity p) {
        return new AdminProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getActive(),
                p.getGroupId()
        );
    }
}