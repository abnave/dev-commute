package com.example.kafka.repository;

import com.example.kafka.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    // Add custom queries if needed
}
