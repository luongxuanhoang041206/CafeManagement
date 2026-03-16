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

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider employeeProvider = new DaoAuthenticationProvider(customEmployeeDetailService);
        employeeProvider.setPasswordEncoder(passwordEncoder);

        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider(userService);
        userProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(employeeProvider, userProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                    "/products/**",
                    "/auth/login",
                    "/auth/register",
                    "/admin/login"
                ).permitAll()
                .requestMatchers("/orders/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
           // .formLogin(form -> form.disable());
            .formLogin(Customizer.withDefaults());
          //  .authenticationProvider(authenticationProvider())
         //   .authenticationProvider(userAuthProvider());

        return http.build();
    }

	

	
}