package com.example.kafka.controller;

import com.example.kafka.model.OrderRequest;
import com.example.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private KafkaProducerService producerService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        producerService.sendOrder(orderRequest);
        return ResponseEntity.ok("Order submitted: " + orderRequest.getOrderId());
    }
}
