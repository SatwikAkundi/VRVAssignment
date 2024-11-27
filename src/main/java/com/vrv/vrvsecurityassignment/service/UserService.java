package com.vrv.vrvsecurityassignment.service;

import com.vrv.vrvsecurityassignment.Model.Users;
import com.vrv.vrvsecurityassignment.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // Marks the class as a service to be managed by Spring's container
public class UserService {

    @Autowired
    private UserRepo repo;  // Injecting the UserRepo to interact with the database

    @Autowired
    private AuthenticationManager authManager;  // Injecting AuthenticationManager to manage authentication

    @Autowired
    private JWTService jwtService;  // Injecting JWTService to generate JWT tokens

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);  // Password encoder for securely hashing passwords

    // Method to verify user credentials and generate JWT token
    public String verify(Users user) {
        // Authenticating the user by creating a UsernamePasswordAuthenticationToken and passing it to the AuthenticationManager
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        // If authentication is successful, generate and return a JWT token
        if (authentication.isAuthenticated()) {
            Users dbUser = repo.findByUsername(user.getUsername());
            return jwtService.generateToken(dbUser.getUsername(), dbUser.getRole());
        }
        // Return "Failure" if authentication fails
        return "Failure";
    }

    // Method to register a new user
    public String register(Users user) {
        // Check if the username is already taken
        if (repo.findByUsername(user.getUsername()) != null) {
            return "User with username " + user.getUsername() + " is already registered.";
        }

        // If no role is specified, set default role as "USER"
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        // If the role is "admin", assign the "ADMIN" role
        if ("admin".equalsIgnoreCase(user.getRole())) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");  // Assign "USER" role if role is not admin
        }

        // Hash the user's password before saving to the database
        user.setPassword(encoder.encode(user.getPassword()));

        // Save the new user to the database
        repo.save(user);

        // Return success message
        return "User has been registered successfully";
    }

}
