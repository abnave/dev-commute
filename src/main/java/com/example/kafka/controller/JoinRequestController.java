package com.example.kafka.controller;

import com.example.kafka.model.JoinRequest;
import com.example.kafka.service.JoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/join-requests")
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    @Autowired
    public JoinRequestController(JoinRequestService joinRequestService) {
        this.joinRequestService = joinRequestService;
    }

    @GetMapping
    public List<JoinRequest> getAllJoinRequests() {
        return joinRequestService.getAllJoinRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JoinRequest> getJoinRequestById(@PathVariable Long id) {
        return joinRequestService.getJoinRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public JoinRequest createJoinRequest(@RequestBody JoinRequest joinRequest) {
        return joinRequestService.createJoinRequest(joinRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoinRequest(@PathVariable Long id) {
        joinRequestService.deleteJoinRequest(id);
        return ResponseEntity.noContent().build();
    }
}
