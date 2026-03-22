package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.response.AdminDashboardResponse;


public interface DashboardService  {
	public AdminDashboardResponse getDashboard(String role);
	
}