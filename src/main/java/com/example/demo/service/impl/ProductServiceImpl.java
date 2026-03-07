package com.example.demo.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.request.UpdateProductRequest;
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
            Pageable pageable  
    ) {

        Specification<ProductEntity> spec =
                ProductSpecification.filter(name, minPrice, maxPrice, active, groupId);

        Page<ProductEntity> productPage = repo.findAll(spec, pageable);

        return productPage.map(mapper::toAdmin);
    }
    // tao moi san pham
    @Override
    public AdminProductResponse create(CreateProductRequest request) {

        ProductEntity product = new ProductEntity();
       // product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setActive(request.getActive());
        product.setGroupId(request.getGroupId());
        product.setCreatedAt(new Date(0));  
        ProductEntity savedProduct = repo.save(product);

        return mapper.toAdmin(savedProduct);
    }
    // Thay doi trang thai
    @Override
    public AdminProductResponse changeStatus(Long id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        product.setActive(!product.getActive());
        ProductEntity updated = repo.save(product);

        return mapper.toAdmin(updated);
    }
    // Xoa san pham
    @Override
    public void deleteProduct(Long id) {
        ProductEntity product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        repo.delete(product);
    }    // Sua san pham
    public AdminProductResponse update(UpdateProductRequest request, Long id) {
    	ProductEntity product = repo.findById(id)
    			.orElseThrow(() -> new RuntimeException("Not Found"));
    	
    	if (request.getName() != null) {
            if (request.getName().isBlank()) {
                throw new RuntimeException("Tên không được rỗng");
            }
            product.setName(request.getName());
        }

        // update price nếu khác null
        if (request.getPrice() != null) {
            if (request.getPrice() <= 0) {
                throw new RuntimeException("Giá phải > 0");
            }
            product.setPrice(request.getPrice());
        }

        // update active nếu khác null
        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }

        // update group nếu khác null
//        if (request.getGroupId() != null) {
//            GroupEntity group = groupRepo.findById(request.getGroupId())
//                    .orElseThrow(() -> new RuntimeException("Group Not Found"));
//
//            product.setGroup(group);
//        }

        ProductEntity savedProduct = repo.save(product);

        return mapper.toAdmin(savedProduct);
    }
    
    // Chi tiet san pham 
    public AdminProductResponse detail(Long id) {
    	ProductEntity product = repo.findById(id)
    			.orElseThrow(() -> new RuntimeException("Not found"));
    	return mapper.toAdmin(product);
    }

	@Override
	public Page<AdminProductResponse> search(String name, Double minPrice, Double maxPrice, Boolean active,
			String groupId, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
    
}