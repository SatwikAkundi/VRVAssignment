package com.vrv.vrvsecurityassignment.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// Custom UserDetails implementation that represents the authenticated user's details
public class UserPrincipal implements UserDetails {

    private Users user; // User object to hold the user data

    // Constructor to initialize the UserPrincipal with a Users object
    public UserPrincipal(Users user) {
        this.user = user;
    }

    // Returns the authorities (roles) of the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a singleton collection containing the user's role
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    // Returns the user's password
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Returns the user's username
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // Indicates whether the account is expired
    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming account is not expired
    }

    // Indicates whether the account is locked
    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming account is not locked
    }

    // Indicates whether the credentials (password) are expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming credentials are not expired
    }

    // Indicates whether the user is enabled (i.e., active)
    @Override
    public boolean isEnabled() {
        return true; // Assuming user is always enabled
    }
}
