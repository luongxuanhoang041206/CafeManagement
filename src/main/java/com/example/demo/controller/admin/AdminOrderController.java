package com.example.demo.controller.admin;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.request.UpdateOrderStatusRequest;
import com.example.demo.dto.response.AdminOrderResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.service.OrderService;
@CrossOrigin(origins = "https://fe-cafe-management-qtup6nb42-luongxuanhoang041206s-projects.vercel.app", allowCredentials = "true")
@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController  {
	private final OrderService service;
	public AdminOrderController(OrderService service) {
		this.service = service;
	}
	// Lay danh sach order + pagi + search
	@GetMapping
	public Page<AdminOrderResponse> search(
	        // filter theo thời gian
	        @RequestParam(required = false) LocalDateTime fromDate,
	        @RequestParam(required = false) LocalDateTime toDate,
	        // filter theo total amount
	        @RequestParam(required = false) Double minTotal,
	        @RequestParam(required = false) Double maxTotal,
	        // filter theo user và employee
	        @RequestParam(required = false) Long userId,
	        @RequestParam(required = false) Long employeeId,
	        // filter theo source và status
	        @RequestParam(required = false) String orderSource,
	        @RequestParam(required = false) String status,
	        // pagination
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        // sort
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction
	) {
	    Sort sort = direction.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);
	    return service.search(
	            fromDate,
	            toDate,
	            minTotal,
	            maxTotal,
	            userId,
	            employeeId,
	            orderSource,
	            status,
	            pageable
	    );
	}
	// tao moi 1 order 
	@PostMapping
	public AdminOrderResponse create(@RequestBody CreateOrderRequest request) {
		return service.create(request); 
	}
	// xem chi tiet 1 order
	@GetMapping("/{id}")
	public AdminOrderResponse detail(@PathVariable Long id ) {
		return service.detail(id);
	}
	// Thay doi trang thai 1 order
	@PatchMapping("/{id}/status")
    public AdminOrderResponse updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        return service.updateStatus(id, request.getStatus());
    }
}