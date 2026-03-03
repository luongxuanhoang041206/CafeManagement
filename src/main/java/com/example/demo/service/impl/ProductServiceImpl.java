package com.example.demo.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.AdminProductResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.ProductEntity;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repo,
                              ProductMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<ProductResponse> getAllForClient() {
        return repo.findByActiveTrue()
                   .stream()
                   .map(mapper::toClient) // goi phuong thuc toClient -> bien no thanh dto tranh lo fields nhay cam, ....
                   .toList();
    }

    @Override
    public List<AdminProductResponse> getAllForAdmin() {
        return repo.findAll()
                   .stream()
                   .map(mapper::toAdmin)
                   .toList();
    }
    @Override
    public List<AdminProductResponse> getAll() {
        return repo.findAll()
                   .stream()
                   .map(mapper::toAdmin)
                   .toList();
    }
//    @Override
//    public List<AdminProductResponse> search(
//            String name,
//            Double minPrice,
//            Double maxPrice,
//            Boolean active,
//            String groupId
//    ) {
//
//        Specification<ProductEntity> spec =
//                ProductSpecification.filter(name, minPrice, maxPrice, active, groupId);
//
//        return repo.findAll(spec)
//                   .stream()
//                   .map(mapper::toAdmin)
//                   .toList();
//    }
    @Override
    public Page<AdminProductResponse> search(
            String name,
            Double minPrice,
            Double maxPrice,
            Boolean active,
            String groupId,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<ProductEntity> spec =
                ProductSpecification.filter(name, minPrice, maxPrice, active, groupId);

        Page<ProductEntity> productPage = repo.findAll(spec, pageable);

        return productPage.map(mapper::toAdmin);
    }
    // tao moi san pham
    @Override
    public AdminProductResponse create(CreateProductRequest request) {

        ProductEntity product = new ProductEntity();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setActive(request.getActive());
        product.setGroupId(request.getGroupId());

        ProductEntity savedProduct = repo.save(product);

        return mapper.toAdmin(savedProduct);
    }
    // Thay doi trang thai
    @Override
    public AdminProductResponse changeStatus(String id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        product.setActive(!product.isActive());
        ProductEntity updated = repo.save(product);

        return mapper.toAdmin(updated);
    }
}