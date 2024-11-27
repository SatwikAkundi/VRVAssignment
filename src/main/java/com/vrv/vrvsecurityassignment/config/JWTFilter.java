package com.vrv.vrvsecurityassignment.config;

import com.vrv.vrvsecurityassignment.service.JWTService;
import com.vrv.vrvsecurityassignment.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService; // Service for handling JWT generation and validation

    @Autowired
    private MyUserDetailsService userDetailsService; // Service for loading user details

    // Overriding the doFilterInternal method from OncePerRequestFilter to intercept the requests
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the "Authorization" header from the HTTP request
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Check if the Authorization header exists and starts with "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            // Extract the JWT token by removing the "Bearer " prefix
            jwtToken = authHeader.substring(7);
            // Extract the username from the JWT token
            username = jwtService.extractUserName(jwtToken);
        }

        // If a username is extracted and the SecurityContext doesn't already have an authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details using the username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // If the JWT token is valid, create an authentication object
            if (jwtService.validateToken(jwtToken, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Set additional details to the authentication object
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
