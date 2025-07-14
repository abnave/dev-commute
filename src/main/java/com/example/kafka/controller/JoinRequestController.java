package com.example.kafka.controller;

import com.example.kafka.model.JoinRequest;
import com.example.kafka.service.JoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing join requests in the Dev Commute application.
 * Provides endpoints for CRUD operations on JoinRequest entities.
 */
@RestController
@RequestMapping("/join-requests")
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    @Autowired
    public JoinRequestController(JoinRequestService joinRequestService) {
        this.joinRequestService = joinRequestService;
    }

    /**
     * Retrieve all join requests.
     * @return list of all join requests
     */
    @GetMapping
    public List<JoinRequest> getAllJoinRequests() {
        return joinRequestService.getAllJoinRequests();
    }

    /**
     * Retrieve a join request by its ID.
     * @param id the join request ID
     * @return the join request if found, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<JoinRequest> getJoinRequestById(@PathVariable Long id) {
        return joinRequestService.getJoinRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new join request.
     * @param joinRequest the join request to create
     * @return the created join request
     */
    @PostMapping
    public JoinRequest createJoinRequest(@RequestBody JoinRequest joinRequest) {
        return joinRequestService.createJoinRequest(joinRequest);
    }

    /**
     * Delete a join request by its ID.
     * @param id the join request ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoinRequest(@PathVariable Long id) {
        joinRequestService.deleteJoinRequest(id);
        return ResponseEntity.noContent().build();
    }
}
