package com.example.kafka.service;

import com.example.kafka.model.JoinRequest;
import com.example.kafka.repository.JoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;

    @Autowired
    public JoinRequestService(JoinRequestRepository joinRequestRepository) {
        this.joinRequestRepository = joinRequestRepository;
    }

    public List<JoinRequest> getAllJoinRequests() {
        return joinRequestRepository.findAll();
    }

    public Optional<JoinRequest> getJoinRequestById(Long id) {
        return joinRequestRepository.findById(id);
    }

    public JoinRequest createJoinRequest(JoinRequest joinRequest) {
        return joinRequestRepository.save(joinRequest);
    }

    public void deleteJoinRequest(Long id) {
        joinRequestRepository.deleteById(id);
    }
}
