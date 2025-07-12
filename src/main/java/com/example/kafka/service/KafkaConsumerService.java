package com.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "${kafka.topic.demo}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        logger.info("Received message: {}", message);
    }
}
