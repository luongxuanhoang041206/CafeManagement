package com.example.demo.controller.accountController;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.RegistrationRequest;
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
    public UserEntity createUser(@RequestBody RegistrationRequest request) {
        UserEntity user = new UserEntity();
        user.setName(request.username); 
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        return userRepository.save(user);
    }
}
