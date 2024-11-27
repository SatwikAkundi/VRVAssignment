package com.vrv.vrvsecurityassignment.service;
import com.vrv.vrvsecurityassignment.Model.Users;
import com.vrv.vrvsecurityassignment.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            Users dbUser = repo.findByUsername(user.getUsername());
            return jwtService.generateToken(dbUser.getUsername(), dbUser.getRole());
        }
        return "Failure";
    }

    public String register(Users user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            return "User with username " + user.getUsername() + " is already registered.";
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        if ("admin".equalsIgnoreCase(user.getRole())) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "User has been registered successfully";
    }

}
