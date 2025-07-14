package com.example.kafka.controller;

import com.example.kafka.model.Ride;
import com.example.kafka.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing rides in the Dev Commute application.
 * Provides endpoints for CRUD operations on Ride entities.
 */
@RestController
@RequestMapping("/rides")
public class RideController {
    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    /**
     * Retrieve all rides.
     * @return list of all rides
     */
    @GetMapping
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    /**
     * Retrieve a ride by its ID.
     * @param id the ride ID
     * @return the ride if found, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRideById(@PathVariable Long id) {
        return rideService.getRideById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new ride.
     * @param ride the ride to create
     * @return the created ride
     */
    @PostMapping
    public Ride createRide(@RequestBody Ride ride) {
        return rideService.createRide(ride);
    }

    /**
     * Delete a ride by its ID.
     * @param id the ride ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }
}
