package com.example.demo.mapper;
import org.springframework.stereotype.Component;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.dto.response.AdminEmployeeResponse;
@Component
public class EmployeeMapper  {
	public AdminEmployeeResponse toAdmin(EmployeeEntity e) {
		return new AdminEmployeeResponse(
				e.getId(),
				e.getName(),
				e.getPhone(),
				e.getPosition(),
				e.getSalary()
		);
	}
}