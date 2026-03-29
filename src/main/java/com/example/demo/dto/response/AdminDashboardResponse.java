package com.example.demo.dto.response;

import java.util.List;


import lombok.Data;

@Data
public class AdminDashboardResponse  {
	private Long totalProducts;
	private Long revenue;
	private Long totalUsers;
	private Long totalEmployee;

}