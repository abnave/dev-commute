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
    private final com.example.kafka.service.KafkaProducerService kafkaProducerService;

    @Autowired
    public RideService(RideRepository rideRepository, com.example.kafka.service.KafkaProducerService kafkaProducerService) {
        this.rideRepository = rideRepository;
        this.kafkaProducerService = kafkaProducerService;
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
        Ride savedRide = rideRepository.save(ride);
        // Publish ride event to Kafka
        com.example.kafka.model.RideEvent event = new com.example.kafka.model.RideEvent(
                savedRide.getId(),
                savedRide.getOwner() != null ? savedRide.getOwner().getUsername() : null,
                savedRide.getVehicleType(),
                savedRide.getAvailableSeats(),
                savedRide.getSource(),
                savedRide.getDestination(),
                savedRide.getStartTime(),
                savedRide.getPrice(),
                savedRide.getStatus()
        );
        kafkaProducerService.sendRideEvent(event);
        return savedRide;
    }

    /**
     * Delete a ride by its ID.
     * @param id the ride ID
     */
    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }
}
