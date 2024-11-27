package com.vrv.vrvsecurityassignment.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

// Entity class representing the Users table in the database
@Entity
@Data  // Lombok annotation to automatically generate getters, setters, toString, and other methods
public class Users {

    @Id  // Marks this field as the primary key in the database
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates the ID field (identity column in database)
    private int id;  // Unique identifier for each user

    private String username;  // The username of the user (used for login)

    private String password;  // The password of the user (stored in a hashed form)

    private String role;  // The role of the user (e.g., USER, ADMIN) for role-based access control (RBAC)
}
