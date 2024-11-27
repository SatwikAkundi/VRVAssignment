package com.vrv.vrvsecurityassignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService; // Service to load user details for authentication

    @Autowired
    private JWTFilter jwtFilter; // JWT filter to handle authentication based on JWT tokens

    // Security configuration for HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Disable CSRF protection as we are using stateless authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register").permitAll()  // Allow everyone to access /register
                        .requestMatchers("/login").permitAll()  // Allow everyone to access /login
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")  // Protect admin pages
                        .requestMatchers("/user/**").hasAuthority("USER")  // Protect user pages
                        .anyRequest().authenticated())  // Other paths require authentication
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless authentication
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before the default authentication filter
                .build();
    }

    // Configure authentication provider with BCryptPasswordEncoder for password encoding and user details service for user lookup
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Password encoder with strength 12
        provider.setUserDetailsService(userDetailsService); // Set the user details service
        return provider;
    }

    // Bean to create and provide an AuthenticationManager, which manages authentication processes
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
