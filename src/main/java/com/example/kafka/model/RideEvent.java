package com.example.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Event model for publishing ride creation events to Kafka.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideEvent {
    private Long id;
    private String ownerUsername;
    private String vehicleType;
    private int availableSeats;
    private String source;
    private String destination;
    private LocalDateTime startTime;
    private Double price;
    private String status;
}
