//package com.example.demo.security;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.entity.AccountEntity;
//import com.example.demo.entity.UserEntity;
//import com.example.demo.repository.AccountRepository;
//import com.example.demo.repository.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final AccountRepository accountRepo;
//
//    public CustomUserDetailsService(AccountRepository accountRepo) {
//        this.accountRepo = accountRepo;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//
//        AccountEntity account = accountRepo.findByUsername(username);
//
//        return new CustomUserDetails(account);
//    }
//}