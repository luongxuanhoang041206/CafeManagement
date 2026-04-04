package com.example.demo.controller.accountController;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.dto.response.RegistrationResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json", produces = "application/json")
    public RegistrationResponse createUser(@RequestBody RegistrationRequest request) {
        // Validate username
        if (userRepository.existsByUsername(request.username)) {
            return new RegistrationResponse(false, "Tên tài khoản đã tồn tại: " + request.username);
        }

        // Validate email
        if (userRepository.existsByEmail(request.email)) {
            return new RegistrationResponse(false, "Email đã tồn tại: " + request.email);
        }

        // Create new user
        UserEntity user = new UserEntity();
        user.setName(request.username);
        user.setUsername(request.username);
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setActive(true);
        user.setRole("USER");
        user.setCreated_at(new java.util.Date());

        userRepository.save(user);
        return new RegistrationResponse(true, "Đăng ký thành công!");
    }
}
