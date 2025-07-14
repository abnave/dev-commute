package com.example.kafka.service;

import com.example.kafka.model.User;
import com.example.kafka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing User entities.
 * Encapsulates business logic for user operations.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all users from the repository.
     * @return list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieve a user by their ID.
     * @param id the user ID
     * @return optional containing the user if found
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieve a user by their username.
     * @param username the username
     * @return the user if found, null otherwise
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Create a new user in the repository.
     * @param user the user to create
     * @return the created user
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Delete a user by their ID.
     * @param id the user ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
