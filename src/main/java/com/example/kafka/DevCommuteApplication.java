package com.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Dev Commute application.
 */
@SpringBootApplication
@EnableScheduling
public class DevCommuteApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevCommuteApplication.class, args);
    }
}
