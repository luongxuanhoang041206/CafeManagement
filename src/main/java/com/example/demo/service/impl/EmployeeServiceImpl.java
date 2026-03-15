package com.example.demo.service.impl;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.CreateEmployeeRequest;
import com.example.demo.dto.request.UpdateEmployeeRequest;
import com.example.demo.dto.response.AdminEmployeeResponse;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.specification.EmployeeSpecification;
import com.example.demo.security.CustomEmployeeDetailService;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository repo;
	private final EmployeeMapper mapper;
	private final PasswordEncoder passwordEncoder;
	public EmployeeServiceImpl(EmployeeRepository repo,
								EmployeeMapper mapper, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder; 
	}
//    public UserService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
	// Lay ra danh sach tat ca
	@Override
	public List<AdminEmployeeResponse> getAll() {
		return repo.findAll()
				.stream()
				.map(mapper::toAdmin)
				.toList();
	}
	// Tim kiem 
	@Override
	public Page<AdminEmployeeResponse> search(
			String name,
			Pageable pageable
	) {
		Specification<EmployeeEntity> spec = 
				EmployeeSpecification.filter(name);
		
		Page<EmployeeEntity> employeePage = repo.findAll(spec, pageable);
		
		return employeePage.map(mapper::toAdmin);
	}
	// tao moi employee
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public AdminEmployeeResponse create(CreateEmployeeRequest request) {
		EmployeeEntity employee = new EmployeeEntity();
	//	employee.setId(request.getId());
		employee.setName(request.getName());
		employee.setPosition(request.getPosition());
		employee.setPhone(request.getPhone());
		employee.setSalary(request.getSalary());
		employee.setUsername(request.getUsername());
	    employee.setPassword(passwordEncoder.encode(request.getPassword()));
	    employee.setRole(request.getRole());
		EmployeeEntity savedEmployee = employee;
		return mapper.toAdmin(savedEmployee);
	}
	// xoa employee
	@Override
	public void deleteEmployee(Long id) {
		EmployeeEntity employee = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found"));;
		repo.deleteById(id);
	}
	// cap nhat employee
	@Override
	public AdminEmployeeResponse update(UpdateEmployeeRequest request, Long id) {
		EmployeeEntity employee = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));
		if(request.getName() != null) {
			if(request.getName().isBlank()) {
				throw new RuntimeException("Name is not blank");
			}
			employee.setName(request.getName());
		}
		
		EmployeeEntity savedEmployee = repo.save(employee);
		
		return mapper.toAdmin(savedEmployee);
	}
	// Chi tiet employee
	@Override
	public AdminEmployeeResponse detail(Long id) {
		EmployeeEntity employee = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found"));
		return mapper.toAdmin(employee);
	}
}