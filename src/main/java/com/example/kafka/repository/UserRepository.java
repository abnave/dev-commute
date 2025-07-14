package com.example.kafka.repository;

import com.example.kafka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entities.
 * Provides CRUD operations and custom queries for users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by their username.
     * @param username the username to search for
     * @return the User if found, null otherwise
     */
    User findByUsername(String username);
}
