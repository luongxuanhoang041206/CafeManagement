package com.example.demo.dto.response;

import java.util.List;

import com.example.demo.entity.ActivityLog;

import lombok.Data;

@Data
public class AdminDashboardResponse  {
	private Long totalProducts;
	private Long revenue;
	private Long totalUsers;
	private Long totalEmployee;
	
	private List<ActivityLog> recentActivities;
}