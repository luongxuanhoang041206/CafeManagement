package com.example.demo.controller.client;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "https://fe-cafe-management-j7rf2tmsi-luongxuanhoang041206s-projects.vercel.app")
@RestController
@RequestMapping("/auth")
public class AuthClientController {
	@Autowired
    @Qualifier("userAuthManager")  // ← inject đúng bean
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
//    public AuthClientController( AuthenticationManager authenticationManager,
//                                UserRepository userRepository,
//                                PasswordEncoder passwordEncoder) {
//        this.authenticationManager = authenticationManager;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//        if (authentication.isAuthenticated()) {
//            return "Login success";
//        }
//        System.out.println(">>> Controller reached! username: " + request.getUsername());
//        System.out.println(">>> Password: " + request.getPassword());
//        return "Login failed";
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = false) LoginRequest request) {
        System.out.println(">>> /auth/login được gọi!");
        System.out.println(">>> Request body: " + request);
        
        if (request == null) {
            System.out.println(">>> REQUEST NULL - body parse thất bại!");
            return ResponseEntity.badRequest().body("Request null");
        }
        
        System.out.println(">>> username: " + request.getUsername());
        System.out.println(">>> password: " + request.getPassword());
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );
            return ResponseEntity.ok("Login success");
            
        } catch (Exception e) {
            System.out.println(">>> EXCEPTION: " + e.getClass().getName());
            System.out.println(">>> MESSAGE: " + e.getMessage());
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public String signup(@RequestBody SignupRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());

        user.setActive(true); // default
        user.setRole("ROLE_USER"); 
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        
        

        return "Register success";
    }
}

//package com.example.demo.controller.client;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.dto.request.LoginRequest;
//import com.example.demo.dto.request.SignupRequest;
//import com.example.demo.service.AuthService;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("/auth")
//public class AuthClientController {
//
//    private final AuthenticationManager authenticationManager;
//    private final AuthService authService;
//
//    public AuthClientController(AuthenticationManager authenticationManager,
//                                AuthService authService) {
//        this.authenticationManager = authenticationManager;
//        this.authService = authService;
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//        if (authentication.isAuthenticated()) {
//            return "Login success";
//        }
//        return "Login failed";
//    }
//
//    @PostMapping("/register")
//    public String signup(@RequestBody SignupRequest request) {
//        return authService.register(request);
//    }
//}