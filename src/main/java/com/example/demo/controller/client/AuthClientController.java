package com.example.demo.controller.client;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.LogoutSupport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthClientController {
	@Autowired
    @Qualifier("userAuthManager")  
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody(required = false) LoginRequest request,
            HttpServletRequest httpRequest) {
        
        if (request == null) {
            return ResponseEntity.badRequest().body("Request null");
        }
        
        try {
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
            
            Optional<UserEntity> userOptional = userRepository.findByUsername(request.getUsername());

            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            UserEntity user = userOptional.get();

            LoginResponse response = new LoginResponse();
            response.setUserId(user.getId());
            response.setUsername(user.getUsername());
            response.setRole("ROLE_USER");

            return ResponseEntity.ok(response);
           
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public String signup(@RequestBody SignupRequest request) {
        String username = request.getUsername() == null ? "" : request.getUsername().trim();
        String email = request.getEmail() == null ? "" : request.getEmail().trim().toLowerCase(Locale.ROOT);
        String password = request.getPassword() == null ? "" : request.getPassword().trim();
        String name = request.getName() == null ? "" : request.getName().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            return "Vui long nhap day du thong tin";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already exists";
        }

        if (userRepository.existsByEmailIgnoreCase(email)) {
            return "Email already exists";
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setName(name);

        user.setActive(true); 
        user.setRole("ROLE_USER"); 
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        
        

        return "Register success";
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpRequest) {
        return LogoutSupport.logout(httpRequest);
    }
}
