package com.example.demo.service.impl;

import com.example.demo.dto.request.PasswordResetRequest;
import com.example.demo.dto.request.TokenRequest;
import com.example.demo.entity.PasswordResetTokenEntity;
import com.example.demo.entity.TokenStatus;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PasswordResetService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final String passwordResetBaseUrl;

    public PasswordResetServiceImpl(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            ObjectProvider<JavaMailSender> mailSenderProvider,
            @Value("${app.password-reset.base-url:https://fe-cafe-management.vercel.app/client/pages/reset-password.html}") String passwordResetBaseUrl) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderProvider = mailSenderProvider;
        this.passwordResetBaseUrl = passwordResetBaseUrl;
    }

    @Override
    public String generateResetToken(TokenRequest tokenRequestDTO) {
        String lookupValue = tokenRequestDTO.info() == null ? "" : tokenRequestDTO.info().trim();
        if (lookupValue.isBlank()) {
            return null;
        }

        UserEntity user = userRepository.findByUsernameOrEmail(lookupValue, lookupValue).orElse(null);
        if (user == null) {
            return null;
        }

        invalidateUnusedTokens(user.getId());

        String resetToken = UUID.randomUUID().toString();

        PasswordResetTokenEntity passwordResetToken = new PasswordResetTokenEntity();
        passwordResetToken.setUserId(user.getId());
        passwordResetToken.setStatus(TokenStatus.UNUSED);
        passwordResetToken.setCreatedAt(LocalDateTime.now());
        passwordResetToken.setExpireAt(LocalDateTime.now().plusMinutes(15));
        passwordResetToken.setToken(resetToken);

        tokenRepository.save(passwordResetToken);
        sendResetEmail(user.getEmail(), resetToken);

        return resetToken;
    }

    @Override
    public boolean isResetTokenValid(String token) {
        PasswordResetTokenEntity passwordResetToken = tokenRepository
                .findByTokenAndStatus(token, TokenStatus.UNUSED)
                .orElse(null);
        return passwordResetToken != null && passwordResetToken.getExpireAt().isAfter(LocalDateTime.now());
    }

    @Override
    public String resetPassword(PasswordResetRequest passwordResetDTO) {
        PasswordResetTokenEntity passwordResetToken = tokenRepository
                .findByTokenAndStatus(passwordResetDTO.token(), TokenStatus.UNUSED)
                .orElse(null);
        if (passwordResetToken == null) {
            return "Token khong hop le";
        }

        if (passwordResetToken.getExpireAt().isBefore(LocalDateTime.now())) {
            passwordResetToken.setStatus(TokenStatus.INACTIVE);
            tokenRepository.save(passwordResetToken);
            return "Token da het han";
        }

        UserEntity user = userRepository.findById(passwordResetDTO.userId()).orElse(null);
        if (user == null) {
            return "Khong tim thay nguoi dung";
        }

        user.setPassword(passwordEncoder.encode(passwordResetDTO.newPassword()));
        userRepository.save(user);

        passwordResetToken.setStatus(TokenStatus.USED);
        tokenRepository.save(passwordResetToken);
        return "Da cap nhat mat khau";
    }

    private void invalidateUnusedTokens(Long userId) {
        List<PasswordResetTokenEntity> activeTokens = tokenRepository.findAllByUserIdAndStatus(userId, TokenStatus.UNUSED);
        for (PasswordResetTokenEntity activeToken : activeTokens) {
            activeToken.setStatus(TokenStatus.INACTIVE);
        }
        if (!activeTokens.isEmpty()) {
            tokenRepository.saveAll(activeTokens);
        }
    }

    private void sendResetEmail(String email, String token) {
    	System.out.println("🔍 sendResetEmail called with email: " + email);
        
        if (email == null || email.isBlank()) {
            System.out.println("⚠️  Email is null or blank");
            return;
        }
 
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        System.out.println("🔍 mailSender available: " + (mailSender != null));
        
        if (mailSender == null) {
            System.out.println("❌ JavaMailSender is NULL! Bean not found in Spring context");
            return;
        }
 
        String separator = passwordResetBaseUrl.contains("?") ? "&" : "?";
        String resetLink = passwordResetBaseUrl + separator + "token=" + token;
        System.out.println("🔗 Reset link: " + resetLink);
 
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset your password: " + resetLink);
            message.setFrom("pnguyencongthanh90@gmail.com");
            
            System.out.println("📧 Sending email to: " + email);
            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + email);
            
        } catch (Exception e) {
            System.out.println("❌ Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
