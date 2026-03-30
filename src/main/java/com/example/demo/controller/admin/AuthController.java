package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.LoginAdminRequest;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.repository.EmployeeRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AuthController  {
	@Autowired
	@Qualifier("employeeAuthManager")
    private AuthenticationManager authenticationManager;
	@Autowired
	private EmployeeRepository employeeRepository;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAdminRequest request, HttpServletRequest httpRequest) {
    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext()
        );
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