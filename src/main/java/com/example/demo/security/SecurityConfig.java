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
        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider(userService);
        userProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(userProvider); // CHỈ 1 provider
    }

    @Bean("employeeAuthManager") 
    public AuthenticationManager employeeAuthManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider employeeProvider = new DaoAuthenticationProvider(customEmployeeDetailService);
        employeeProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(employeeProvider); // CHỈ 1 provider
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	System.out.println(">>> SecurityFilterChain được load!");
    	http
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
//            // 1. OPTIONS
//            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//            
//            // 2. Public endpoints
//            .requestMatchers(
//                "/products/**",
//                "/auth/login",
//                "/auth/register",
//                "/admin/login",
//                "/v3/api-docs/**"
//            ).permitAll()
//            
//            // 3. Product permissions
//            .requestMatchers(HttpMethod.GET, "/admin/products/**")
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF")
//            .requestMatchers(HttpMethod.POST, "/admin/products/**")
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
//            .requestMatchers(HttpMethod.PUT, "/admin/products/**")
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
//            .requestMatchers(HttpMethod.DELETE, "/admin/products/**")
//                .hasAnyAuthority("ROLE_ADMIN")
//
//            // 4. Orders
//            .requestMatchers("/admin/orders/**")
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF")
//
//            // 5. Employees
//            .requestMatchers(HttpMethod.GET, "/admin/employees/**")
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
//            .requestMatchers("/admin/employees/**")
//                .hasAnyAuthority("ROLE_ADMIN")
//
//            // 6. Users
//            .requestMatchers("/admin/users/**")
//                .hasAnyAuthority("ROLE_ADMIN")
//            
//            // 7. Admin endpoints
//            .requestMatchers("/admin/**").authenticated()
//            
//            // 8. Mặc định
//            .anyRequest().authenticated()
        		 .anyRequest().permitAll()
        )
        .formLogin(form -> form.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(
                org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
        );
        return http.build();
    }

	

	
}