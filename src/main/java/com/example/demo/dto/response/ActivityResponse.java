package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivityResponse  {
	private Long id;
	private String type;
    private String action;
    private String message;
    private LocalDateTime createdAt;
	public ActivityResponse(Long id,String type, String action, String message, LocalDateTime createdAt) {
		this.id = id;
		this.type = type;
		this.action = action;
		this.message = message;
		this.createdAt = createdAt;
	}
    
    
}