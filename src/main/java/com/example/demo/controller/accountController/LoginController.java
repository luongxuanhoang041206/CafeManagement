package com.example.demo.controller.accountController;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/req/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        return userRepository.findByName(loginRequest.username)
            .map(user -> {
                if (passwordEncoder.matches(loginRequest.password, user.getPassword())) {
                    return ResponseEntity.ok("Đăng nhập thành công!");
                } else {
                    return ResponseEntity.status(401).body("Sai mật khẩu!");
                }
            })
            .orElse(ResponseEntity.status(401).body("Không tìm thấy người dùng!"));
    }
    
}
