
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomEmployeeDetailService customEmployeeDetailService;
    private final CustomUserDetailsService userService;

    public SecurityConfig(CustomEmployeeDetailService customEmployeeDetailService,
                          CustomUserDetailsService userService) {
        this.customEmployeeDetailService = customEmployeeDetailService;
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

 // SecurityConfig.java — CHỈ dùng userProvider cho /auth/login
    @Bean("userAuthManager")
    @Primary
    public AuthenticationManager userAuthManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
        userProvider.setUserDetailsService(userService);
        userProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(userProvider); 
    }

    @Bean("employeeAuthManager") 
    public AuthenticationManager employeeAuthManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider employeeProvider = new DaoAuthenticationProvider();
        employeeProvider.setUserDetailsService(customEmployeeDetailService);
        employeeProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(employeeProvider); 
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOrigin("http://localhost:3000");
                config.addAllowedOrigin("http://localhost:10000/v3/api-docs");
                config.addAllowedOrigin("http://localhost:10000/swagger-ui.html");
                config.addAllowedOrigin("https://fe-cafe-management.vercel.app");
                config.addAllowedOrigin("https://*.vercel.app");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                config.setAllowCredentials(true);
                return config;
            }))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                    ).permitAll()
                .requestMatchers(
                    "/products/**",
                    "/auth/login",
                    "/auth/register",
                    "/forgot-password",
                    "/auth/forgot-password",
                    "/reset-password",
                    "/auth/reset-password",
                    "/resetPassword",
                    "/auth/resetPassword",
                    "/reset-password/validate",
                    "/auth/reset-password/validate",
                    "/requestResetToken",
                    "/auth/api/requestResetToken",
                    "/api/resetPassword",
                    "/auth/api/resetPassword",
                    "/auth/logout",
                    "/admin/login",
                    "/admin/logout"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/admin/products/**")
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF")
                .requestMatchers(HttpMethod.POST, "/admin/products/**")
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .requestMatchers(HttpMethod.PATCH, "/admin/products/**")
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/admin/products/**")
                    .hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/order/**", "/admin/orders/**")
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF")
                .requestMatchers(HttpMethod.GET, "/admin/employees/**")
                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .requestMatchers("/admin/employees/**")
                    .hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/admin/users/**")
                    .hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(
                    org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
            );
        return http.build();
    }
}
