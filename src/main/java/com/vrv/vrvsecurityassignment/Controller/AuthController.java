package com.vrv.vrvsecurityassignment.Controller;

import com.vrv.vrvsecurityassignment.Model.Users;
import com.vrv.vrvsecurityassignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Marks this class as a REST controller to handle HTTP requests and responses
public class AuthController {

    @Autowired
    private UserService userService; // Injects the UserService for handling user-related actions

    // Endpoint for user registration
    @PostMapping("/register") // Maps POST requests to /register to this method
    public String register(@RequestBody Users user) { // Accepts a Users object in the request body
        return userService.register(user); // Registers the user and returns the result
    }

    // Endpoint for user login
    @PostMapping("/login") // Maps POST requests to /login to this method
    public String login(@RequestBody Users user) { // Accepts a Users object in the request body
        return userService.verify(user); // Verifies the user credentials and returns the JWT token
    }
}
