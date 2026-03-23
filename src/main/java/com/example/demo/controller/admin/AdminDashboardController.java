package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.AdminDashboardResponse;
import com.example.demo.service.DashboardService;

@CrossOrigin(origins = "https://fe-cafe-management-j7rf2tmsi-luongxuanhoang041206s-projects.vercel.app", allowCredentials = "true")
@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping()
	public AdminDashboardResponse getDashboard(Authentication auth) {
		String role = auth.getAuthorities().iterator().next().getAuthority();
		return dashboardService.getDashboard(role);
	}

}