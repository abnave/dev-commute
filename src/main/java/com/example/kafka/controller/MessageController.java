package com.example.kafka.controller;

import com.example.kafka.model.MessageRequest;
import com.example.kafka.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final KafkaProducerService producerService;

    public MessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest messageRequest) {
        producerService.sendMessage(messageRequest);
        return ResponseEntity.ok("Message sent to Kafka topic.");
    }
}
