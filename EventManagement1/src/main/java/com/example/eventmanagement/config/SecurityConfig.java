package com.example.eventmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/api-docs/**",
                    "/actuator/health"
                ).permitAll()

                .requestMatchers("/api/events/**")
                    .hasRole("ORGANIZER")

                .requestMatchers("/api/sessions/**")
                    .hasRole("ORGANIZER")

                .requestMatchers("/api/attendees/**")
                    .hasRole("ORGANIZER")

                .requestMatchers("/api/registrations/**")
                    .hasRole("ORGANIZER")

                .anyRequest().authenticated()
            )

            .httpBasic(basic -> {});

        return http.build();
    }
}