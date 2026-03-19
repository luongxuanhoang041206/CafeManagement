package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.EmployeeEntity;

import java.util.Collection;
import java.util.List;

public class CustomEmployeeDetails implements UserDetails {

    private EmployeeEntity employee;

    public CustomEmployeeDetails(EmployeeEntity employee) {
        this.employee = employee;
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return List.of(new SimpleGrantedAuthority(employee.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}