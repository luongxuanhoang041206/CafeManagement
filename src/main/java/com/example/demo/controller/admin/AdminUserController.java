package com.example.demo.controller.admin;

import org.springframework.data.domain.PageRequest;

import com.example.demo.dto.response.AdminUserResponse;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin/user")

public class AdminUserController  {
	private final UserService service;
	
	public AdminUserController(UserService service) {
		this.service = service;
	}
	
	// Lay danh sach user
	@GetMapping
	public Page<AdminUserResponse> search(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="6") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		return service.search(name, pageable);
	}
	// Doi trang thai user
	@PatchMapping("/{id}/active")
	public void changeActive(@PathVariable Long id) {
		service.changeActive(id);
	}
	// Xoa user
	@DeleteMapping("/{id}/delete")
	public void deleteUser(@PathVariable Long id) {
		service.deleteUser(id);
	}
}