package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.AdminDashboardResponse;
import com.example.demo.entity.ActivityLog;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ActivityRepository activityRepository;

	@Override
	public AdminDashboardResponse getDashboard(String role) {
		AdminDashboardResponse res = new AdminDashboardResponse();
		
		res.setTotalProducts(productRepository.count());
		res.setTotalEmployee(employeeRepository.count());
		res.setTotalUsers(userRepository.count());
		
		Long revenue = paymentRepository.getTotalRevenue();
		res.setRevenue(revenue != null ? revenue : 0);
		
		List<ActivityLog> activities = activityRepository.findTop10ByOrderByCreatedAtDesc();
		
		if("ROlE_MANAGER".equals(role)) {
			activities = activities.stream()
					.filter(a -> !a.getType().equals("USER"))
					.toList();
		}
		
		if("ROLE_STAFF".equals(role)) {
			activities = activities.stream()
					.filter(a -> !a.getType().equals("ORDER"))
					.toList();
		}
		
		res.setRecentActivities(activities);
		return res;
	}

}