package com.example.demo.service;

import org.springframework.stereotype.Service;

public interface ActivityService  {
	 public void log(String type, String action, String message);
}