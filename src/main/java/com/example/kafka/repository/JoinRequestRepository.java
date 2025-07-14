package com.example.kafka.repository;

import com.example.kafka.model.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for JoinRequest entities.
 * Provides CRUD operations for join requests.
 */
@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    // Add custom queries if needed
}
