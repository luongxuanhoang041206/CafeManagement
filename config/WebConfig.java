
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Liệt kê đúng các origin được phép
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://fe-cafe-management-j7rf2tmsi-luongxuanhoang041206s-projects.vercel.app");
        // Thêm cả domain Vercel gốc nếu có
        // config.addAllowedOrigin("https://fe-cafe-management.vercel.app");
        
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    // Xóa hoàn toàn corsConfigurationSource() bean
}