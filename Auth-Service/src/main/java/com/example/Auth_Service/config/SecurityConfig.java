package com.example.Auth_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain Filter(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll().anyRequest().permitAll())
                .csrf(csrf -> csrf.disable()).headers(headers -> headers.frameOptions().disable()); ;
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder(14);
    }
}
