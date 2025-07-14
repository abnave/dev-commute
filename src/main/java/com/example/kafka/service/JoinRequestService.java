package com.example.kafka.service;

import com.example.kafka.model.JoinRequest;
import com.example.kafka.repository.JoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing JoinRequest entities.
 * Encapsulates business logic for join request operations.
 */
@Service
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;

    @Autowired
    public JoinRequestService(JoinRequestRepository joinRequestRepository) {
        this.joinRequestRepository = joinRequestRepository;
    }

    /**
     * Retrieve all join requests from the repository.
     * @return list of join requests
     */
    public List<JoinRequest> getAllJoinRequests() {
        return joinRequestRepository.findAll();
    }

    /**
     * Retrieve a join request by its ID.
     * @param id the join request ID
     * @return optional containing the join request if found
     */
    public Optional<JoinRequest> getJoinRequestById(Long id) {
        return joinRequestRepository.findById(id);
    }

    /**
     * Create a new join request in the repository.
     * @param joinRequest the join request to create
     * @return the created join request
     */
    public JoinRequest createJoinRequest(JoinRequest joinRequest) {
        return joinRequestRepository.save(joinRequest);
    }

    /**
     * Delete a join request by its ID.
     * @param id the join request ID
     */
    public void deleteJoinRequest(Long id) {
        joinRequestRepository.deleteById(id);
    }
}
