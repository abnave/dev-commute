package com.example.kafka.service;

import com.example.kafka.model.Ride;
import com.example.kafka.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Ride entities.
 * Encapsulates business logic for ride operations.
 */
@Service
public class RideService {
    private final RideRepository rideRepository;

    @Autowired
    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    /**
     * Retrieve all rides from the repository.
     * @return list of rides
     */
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    /**
     * Retrieve a ride by its ID.
     * @param id the ride ID
     * @return optional containing the ride if found
     */
    public Optional<Ride> getRideById(Long id) {
        return rideRepository.findById(id);
    }

    /**
     * Create a new ride in the repository.
     * @param ride the ride to create
     * @return the created ride
     */
    public Ride createRide(Ride ride) {
        return rideRepository.save(ride);
    }

    /**
     * Delete a ride by its ID.
     * @param id the ride ID
     */
    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }
}
