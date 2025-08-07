package com.Ecommerce.ApliServi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Permitir solicitudes desde cualquier origen
        config.addAllowedMethod("*"); // Permitir todos los métodos HTTP
        config.addAllowedHeader("*"); // Permitir todos los encabezados
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}