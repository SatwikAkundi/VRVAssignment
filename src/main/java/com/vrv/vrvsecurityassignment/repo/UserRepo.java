package com.vrv.vrvsecurityassignment.repo;

import com.vrv.vrvsecurityassignment.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for CRUD operations on the Users entity
@Repository  // Marks this interface as a repository to be recognized by Spring Data JPA
public interface UserRepo extends JpaRepository<Users, Integer> {

    // Custom query method to find a user by their username
    Users findByUsername(String username);  // Finds a user based on their username
}
