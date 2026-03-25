package com.example.demo.controller.client;

import java.time.LocalDateTime;
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

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());

        user.setActive(true); 
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