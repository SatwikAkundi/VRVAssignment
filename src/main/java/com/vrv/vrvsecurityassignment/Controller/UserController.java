package com.vrv.vrvsecurityassignment.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller to handle HTTP requests and responses
@RequestMapping("/user") // Maps all requests starting with /user to this controller
public class UserController {

    // Endpoint for user dashboard
    @GetMapping("/dashboard") // Maps GET requests to /user/dashboard to this method
    public String userDashboard() {
        return "Welcome User! You have limited access."; // Returns a welcome message for the user
    }
}
