package com.example.kafka.controller;

import com.example.kafka.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final KafkaProducerService producerService;

    public MessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        producerService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka topic.");
    }
}
