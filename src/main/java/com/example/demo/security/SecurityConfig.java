//package com.example.demo.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//        .cors(Customizer.withDefaults()) 
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//            		
//            	.requestMatchers(
//                      "/v3/api-docs/**",
//                      "/swagger-ui/**",
//                      "/swagger-ui.html"
//                    ).permitAll()
//       
////                // PRODUCT
////                .requestMatchers(HttpMethod.GET, "/admin/products/**")
////                    .hasAnyRole("ADMIN", "MANAGER")
////
////                .requestMatchers(HttpMethod.POST, "/admin/products/**")
////                    .hasRole("ADMIN")
////
////                .requestMatchers(HttpMethod.PATCH, "/admin/products/**")
////                    .hasAnyRole("ADMIN", "MANAGER")
////
////                .requestMatchers(HttpMethod.DELETE, "/admin/products/**")
////                    .hasRole("ADMIN")
////
////                // USER MANAGEMENT
////                .requestMatchers("/admin/users/**")
////                    .hasRole("ADMIN")
//
//               // .anyRequest().authenticated()
//            	.anyRequest().permitAll()
//            );
//
//        return http.build();
//    }
//}
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.UserService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    //cau hinh co ban, tat cors, tat csrf, cho phep tat ca request ma khong can xac thuc(ban can xac thuc o duoi)
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    //     http
    //         .cors(Customizer.withDefaults())   // 🔥 QUAN TRỌNG
    //         .csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth
    //             .anyRequest().permitAll()
    //         );

    //     return http.build();
    // }

    //phuc vu cho login, signup
    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) 

                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/req/login", "/req/signup", "/css/**", "/js/**","/index", "/").permitAll();
                    registry.anyRequest().authenticated();
                })

                .formLogin(httpForm -> {
                    httpForm
                        .loginPage("/req/login")
                        .permitAll();
                    httpForm
                        .defaultSuccessUrl("/index", true);
                })
                .build();
    }
}