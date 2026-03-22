package com.example.demo.controller.admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.request.CreateEmployeeRequest;
import com.example.demo.dto.request.UpdateEmployeeRequest;
import com.example.demo.dto.response.AdminEmployeeResponse;
import com.example.demo.service.EmployeeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "https://fe-cafe-management-qtup6nb42-luongxuanhoang041206s-projects.vercel.app", allowCredentials = "true")
@RestController
@RequestMapping("/admin/employee")

public class AdminEmployeeController  {
	private final EmployeeService service;
	
	public AdminEmployeeController(EmployeeService service) {
		this.service = service;
	}
	
	// Lay danh sach employee 
	@GetMapping
	public Page<AdminEmployeeResponse> search(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction 
	) {
		Sort sort = direction.equalsIgnoreCase("asc")
				?Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size, sort);
		return service.search(name, pageable);
	}
	// tao moi employee
//	@PostMapping
//	public AdminEmployeeResponse create(@RequestBody CreateEmployeeRequest request) {
//		return service.create(request);
//	}
	@PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AdminEmployeeResponse create(@RequestBody CreateEmployeeRequest request) {
         return service.create(request);
    }
	// Xoa employee
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {
		service.deleteEmployee(id);
		return "Deleted successfully";
	}
	// Cap nhat employee
	@PatchMapping("/{id}")
	public AdminEmployeeResponse update(@RequestBody UpdateEmployeeRequest request,
			@PathVariable Long id) {
		return service.update(request, id);
	} 
	// Xem chi tiet employee
	@GetMapping("/{id}")
	public AdminEmployeeResponse detail(@PathVariable Long id) {
		return service.detail(id);
	}
} 