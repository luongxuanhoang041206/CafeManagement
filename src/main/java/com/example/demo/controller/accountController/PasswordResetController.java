package com.example.demo.controller.accountController;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.TokenRequestDTO;
import com.example.demo.dto.request.PasswordResetDTO;
import com.example.demo.dto.response.TokenResponseDTO;
import com.example.demo.service.PasswordResetService;
import com.example.demo.repository.TokenRepository;
import com.example.demo.entity.PasswordResetTokenEntity;
import com.example.demo.entity.TokenStatus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import java.time.LocalDateTime;



@Controller
public class PasswordResetController {
    private PasswordResetService passwordResetService;
    private TokenRepository tokenRepository;


    public PasswordResetController(PasswordResetService passwordResetService, TokenRepository tokenRepository) {
        this.passwordResetService = passwordResetService;
        this.tokenRepository = tokenRepository;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgotpassword";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        PasswordResetTokenEntity resetToken = tokenRepository.findByTokenAndStatus(token, TokenStatus.UNUSED).orElse(null);
        if (resetToken == null) {
            return "redirect:/forgot-password?error=invalid";
        }

        // kiem tra het han chua, neu het han thi redirect ve trang forgot-password va hien thong bao het han
        if (resetToken.getExpireAt().isBefore(LocalDateTime.now())) {
            return "redirect:/forgot-password?error=expired";
        }

        model.addAttribute("token", token);
        model.addAttribute("userId", resetToken.getUserId());
        return "resetpassword";
    }

    @PostMapping("/api/requestResetToken")
    @ResponseBody
    public TokenResponseDTO requestReset(@RequestBody TokenRequestDTO tokenRequestDTO) {
        String token = passwordResetService.generateResetToken(tokenRequestDTO);
        return new TokenResponseDTO(token);
    }
    
    @PostMapping("/api/resetPassword")
    @ResponseBody
    public String resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        return passwordResetService.resetPassword(passwordResetDTO);
    }
    
}

