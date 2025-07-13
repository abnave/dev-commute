package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.orders}")
    private String ordersTopic;

    @Value("${kafka.topic.partitions}")
    private int partitions;

    @Bean
    public NewTopic ordersTopic() {
        return new NewTopic(ordersTopic, partitions, (short) 1);
    }
}
