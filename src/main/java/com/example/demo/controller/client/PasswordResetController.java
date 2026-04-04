package com.example.demo.controller.client;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.PasswordResetRequest;
import com.example.demo.dto.request.TokenRequest;
import com.example.demo.dto.response.TokenReponse;
import com.example.demo.service.PasswordResetService;
import com.example.demo.repository.TokenRepository;

@RestController
@CrossOrigin(origins = "https://fe-cafe-management.vercel.app")  
public class PasswordResetController {
    private final PasswordResetService passwordResetService;
    private final TokenRepository tokenRepository;

    public PasswordResetController(PasswordResetService passwordResetService, TokenRepository tokenRepository) {
        this.passwordResetService = passwordResetService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/requestResetToken")
    public ResponseEntity<?> requestReset(@RequestBody TokenRequest tokenRequestDTO) {
        String token = passwordResetService.generateResetToken(tokenRequestDTO);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Khong tim thay tai khoan"));
        }
        return ResponseEntity.ok(new TokenReponse(token));
    }

    // Endpoint: GET /api/reset-password/validate?token=xxx
    @GetMapping("/reset-password/validate")
    public ResponseEntity<?> validateResetToken(@RequestParam("token") String token) {
        if (!passwordResetService.isResetTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Token khong hop le hoac da het han"));
        }
        return tokenRepository.findByTokenAndStatus(token, com.example.demo.entity.TokenStatus.UNUSED)
                .map(resetToken -> ResponseEntity.ok(Map.of(
                        "message", "Token hop le",
                        "userId", resetToken.getUserId(),
                        "token", token)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Token khong hop le hoac da het han")));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetDTO) {
        String result = passwordResetService.resetPassword(passwordResetDTO);
        HttpStatus status = "Da cap nhat mat khau".equals(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(Map.of("message", result));
    }
}