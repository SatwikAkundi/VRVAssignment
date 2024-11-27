package com.vrv.vrvsecurityassignment.service;

import com.vrv.vrvsecurityassignment.Model.UserPrincipal;
import com.vrv.vrvsecurityassignment.Model.Users;
import com.vrv.vrvsecurityassignment.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service  // Marks this class as a service to be managed by Spring's container
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;  // Inject the UserRepo to interact with the database

    // Overriding the loadUserByUsername method to load the user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by username from the repository
        Users user = repo.findByUsername(username);

        // If user is not found, throw UsernameNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return the user details encapsulated in a UserPrincipal object
        return new UserPrincipal(user);
    }
}
