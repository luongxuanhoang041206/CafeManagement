package com.example.demo.service.impl;

import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PasswordResetService;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.PasswordResetDTO;
import com.example.demo.dto.request.TokenRequestDTO;
import com.example.demo.entity.PasswordResetTokenEntity;
import com.example.demo.entity.TokenStatus;
import com.example.demo.entity.UserEntity;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public PasswordResetServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

        public String generateResetToken(TokenRequestDTO tokenRequestDTO){
            UserEntity user = userRepository.findByNameOrEmail(tokenRequestDTO.info(), tokenRequestDTO.info()).orElse(null);

            if(user != null){
                String resetToken = UUID.randomUUID().toString();

                PasswordResetTokenEntity passwordResetTokenE = new PasswordResetTokenEntity();
                passwordResetTokenE.setUserId(user.getId());
                passwordResetTokenE.setStatus(TokenStatus.UNUSED);
                passwordResetTokenE.setCreateAt(LocalDateTime.now());
                passwordResetTokenE.setToken(resetToken);

                tokenRepository.save(passwordResetTokenE);

                // Gui email + link reset mat khau
                sendResetEmail(user.getEmail(), resetToken);

                return resetToken;
            }
            return null;
        }

        private void sendResetEmail(String email, String token) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset your password: http://localhost:8080/resetPassword?token=" + token);
            mailSender.send(message);
        }

        //tao link reset mat khau
        public String resetPassword(PasswordResetDTO passwordResetDTO){
            PasswordResetTokenEntity passwordResetTokenE = tokenRepository.findByTokenAndStatus(passwordResetDTO.token(), TokenStatus.UNUSED).orElse(null);
            if(passwordResetTokenE == null){
                return "Token khong hop le";
            }

            //set cho thanh dung roi
            passwordResetTokenE.setStatus(TokenStatus.USED);
            tokenRepository.save(passwordResetTokenE);

            userRepository.findById(passwordResetDTO.userId()).ifPresent(user -> {
                user.setPassword(passwordEncoder.encode(passwordResetDTO.newPassword()));
                userRepository.save(user);
            });
            return "Da cap nhat ma khau";
        }
}
