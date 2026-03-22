package com.example.demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ActivityLog;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {

	 	@Autowired
	    private ActivityRepository repo;

	    public void log(String type, String action, String message) {
	        ActivityLog log = new ActivityLog();
	        log.setType(type);
	        log.setAction(action);
	        log.setMessage(message);
	        log.setCreatedAt(LocalDateTime.now());

	        repo.save(log);
	    }
}