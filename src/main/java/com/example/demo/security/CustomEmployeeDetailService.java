package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.EmployeeEntity;
import com.example.demo.repository.EmployeeRepository;

@Service
public class CustomEmployeeDetailService implements UserDetailsService {
// do db va spring ko noi cung ngon ngu 
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        EmployeeEntity employee = employeeRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // convert sang userDetail
        return User.builder()
                .username(employee.getUsername()) 
                .password(employee.getPassword())
                .authorities(employee.getRole().name())
                .build();
    }
}