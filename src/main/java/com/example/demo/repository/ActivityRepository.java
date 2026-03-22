package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ActivityLog;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findTop10ByOrderByCreatedAtDesc();
}