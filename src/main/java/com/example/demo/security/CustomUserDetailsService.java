package com.example.demo.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//
//        UserEntity user = userRepo.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(user.getPassword())
//                .authorities(user.getRole())
//                .build();
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> UserDetailsService tìm: '" + username + "'");
        
        Optional<UserEntity> found = userRepo.findByUsername(username);
        System.out.println(">>> Tìm thấy không: " + found.isPresent());
        
        UserEntity user = found.orElseThrow(() -> {
            System.out.println(">>> KHÔNG TÌM THẤY USER: '" + username + "'");
            return new UsernameNotFoundException("User not found");
        });
        
        System.out.println(">>> Username trong DB: '" + user.getUsername() + "'");
        System.out.println(">>> Password trong DB: '" + user.getPassword() + "'");
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
    }
}