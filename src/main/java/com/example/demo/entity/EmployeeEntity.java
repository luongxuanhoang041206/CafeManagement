package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class EmployeeEntity {

    @Id
    private Long id;

    private String name;
    private String position;
    private String phone;
    private String salary;

//    @Enumerated(EnumType.STRING)
//    private Role role;
}