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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults())   // 🔥 QUAN TRỌNG
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );

        return http.build();
    }
}