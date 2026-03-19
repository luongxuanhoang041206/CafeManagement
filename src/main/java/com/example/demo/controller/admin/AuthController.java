package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.LoginAdminRequest;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AuthController  {
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
	private EmployeeRepository employeeRepository;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAdminRequest request) {
    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        System.out.println(request.getUsername());
//        String username = authentication.getName(); //lay tu authentication.getPrincipal()
//        System.out.println(username);
        EmployeeEntity employee = employeeRepository.findByUsername(request.getUsername())
        		.orElseThrow(() -> new RuntimeException("Employee not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("id", employee.getId().toString());
        response.put("username", employee.getUsername());
        response.put("role", authentication.getAuthorities()
            .stream().findFirst()
            .map(a -> a.getAuthority())
            .orElse("UNKNOWN"));

        return ResponseEntity.ok(response);
    }
}