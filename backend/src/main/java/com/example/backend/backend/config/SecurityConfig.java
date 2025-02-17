package com.example.backend.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF (útil en desarrollo; en producción revisa su configuración)
                .csrf(csrf -> csrf.disable())
                // Configuramos la autorización de peticiones
                .authorizeHttpRequests(auth -> auth
                        // Permitimos el acceso a cualquier endpoint de los siguientes
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/api/v1/accounts/**").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/swagger-config").permitAll()
                        .requestMatchers("/api/v1/transaction/**").permitAll()
                        // Todas las demás solicitudes requieren autenticación
                        .anyRequest().authenticated()
                )
                // Deshabilitamos el formulario de login por defecto
                .formLogin(form -> form.disable())
                // Deshabilitamos la autenticación básica
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
