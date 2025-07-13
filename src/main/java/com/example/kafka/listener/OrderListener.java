package com.example.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {
    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @KafkaListener(
        topics = "${kafka.topic.orders}",
        groupId = "order-consumer-group",
        concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> record) {
        logger.info("[OrderListener] Thread: {} | Partition: {} | Offset: {} | Value: {}",
                Thread.currentThread().getName(),
                record.partition(),
                record.offset(),
                record.value());
        // Here you would deserialize and process the order
    }
}
