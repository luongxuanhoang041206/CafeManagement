//package com.example.demo.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.Customizer;
//
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//            .cors(Customizer.withDefaults())   
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/admin/**", "/**").permitAll()
//                .anyRequest().permitAll()
//            );
//
//        return http.build();
//    }
//}
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomEmployeeDetailService customEmployeeDetailService;

    public SecurityConfig(CustomEmployeeDetailService customEmployeeDetailService) {
        this.customEmployeeDetailService = customEmployeeDetailService;
    }

    // UserDetailsService dùng để load user khi login
    @Bean
    public UserDetailsService userDetailsService() {
        return customEmployeeDetailService;
    }

    // mã hóa password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // provider xử lý login
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customEmployeeDetailService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    // cấu hình security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        	.cors(Customizer.withDefaults())   
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/admin/login").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )

//            .formLogin(form -> form
//                    .loginProcessingUrl("/login")
//                    .permitAll()
//            )

            .authenticationProvider(authenticationProvider());

        return http.build();
    }
}