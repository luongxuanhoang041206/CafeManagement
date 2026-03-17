//package com.example.demo.security;
//
//import java.util.Collection;
//import java.util.List;
//
//import org.jspecify.annotations.Nullable;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.example.demo.entity.AccountEntity;
//import com.example.demo.entity.UserEntity;
//
//public class CustomUserDetails implements UserDetails {
//
//    private final AccountEntity account;
//
//    public CustomUserDetails(AccountEntity account) {
//        this.account = account;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(
//            new SimpleGrantedAuthority(
//                "ROLE_" + account.getUser().getRole().name()
//            )
//        );
//    }
//
//    @Override
//    public String getPassword() {
//        return account.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return account.getUsername();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return account.isActive();
//    }
//}