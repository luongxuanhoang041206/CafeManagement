package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class UserEntity  {
	@Id
	private Long id;
	private String name;
	private String password;
	private String email;
	private boolean active;
	private Date created_at;
}