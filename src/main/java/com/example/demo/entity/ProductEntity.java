package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private double price;
    @Column(name="group_id")
    private Integer groupId;
    private Boolean deleted;
    private Boolean active;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    private Date deletedAt;
}