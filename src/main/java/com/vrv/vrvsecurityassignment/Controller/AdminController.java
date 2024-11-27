package com.vrv.vrvsecurityassignment.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indicates that this class is a REST controller that handles HTTP requests and responses
@RequestMapping("/admin") // Base URL for this controller is "/admin"
public class AdminController {

    // Endpoint to access the admin dashboard
    @GetMapping("/dashboard") // Maps GET requests to /admin/dashboard to this method
    public String adminDashboard() {
        return "Welcome Admin! You have privileged access."; // Returns a message for the admin dashboard
    }
}
