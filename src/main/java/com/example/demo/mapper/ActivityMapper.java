package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.response.ActivityResponse;
import com.example.demo.entity.ActivityLog;
@Component
public class ActivityMapper  {
	public ActivityResponse toAdmin(ActivityLog a) {
		return new ActivityResponse(
				a.getId(),
				a.getType(),
				a.getAction(),
				a.getMessage(),
				a.getCreatedAt()
		);
	}
}