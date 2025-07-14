package com.example.kafka.service;

import com.example.kafka.model.MessageRequest;
import com.example.kafka.model.OrderRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.time.LocalDateTime;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;
    private final int partitions;
    private final String headerMessageType;
    private final String headerUser;

    private final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
        .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString()))
        .create();

    public KafkaProducerService(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${kafka.topic.demo}") String topic,
            @Value("${kafka.topic.partitions}") int partitions,
            @Value("${kafka.headers.messageType}") String headerMessageType,
            @Value("${kafka.headers.user}") String headerUser) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.partitions = partitions;
        this.headerMessageType = headerMessageType;
        this.headerUser = headerUser;
    }

    /**
     * Enhanced sendMessage supporting:
     * - custom timestamp
     * - transactional publishing
     * - arbitrary headers (if present in MessageRequest)
     * - advanced error handling
     */
    public void sendMessage(MessageRequest messageRequest) {
        String key = messageRequest.getId(); // Use id as key
        String value = gson.toJson(messageRequest);

        // Optionally, set partition by extracting from id (if numeric), else let Kafka decide
        Integer partition = null;
        try {
            partition = Integer.parseInt(key) % partitions;
        } catch (Exception ignored) {}

        // Custom timestamp: use from request if present, else current time
        Long timestamp = messageRequest.getTimestamp() != null ? messageRequest.getTimestamp() : System.currentTimeMillis();

        // Build ProducerRecord with all advanced features
        ProducerRecord<String, String> record = new ProducerRecord<>(
                topic,
                partition,
                timestamp,
                key,
                value
        );
        // Standard headers
        if (messageRequest.getMessageType() != null) {
            record.headers().add(new RecordHeader(headerMessageType, messageRequest.getMessageType().getBytes()));
        }
        if (messageRequest.getUser() != null) {
            record.headers().add(new RecordHeader(headerUser, messageRequest.getUser().getBytes()));
        }
        // Arbitrary custom headers (if present)
        if (messageRequest.getHeaders() != null) {
            for (Map.Entry<String, String> entry : messageRequest.getHeaders().entrySet()) {
                record.headers().add(new RecordHeader(entry.getKey(), entry.getValue().getBytes()));
            }
        }

        // Transactional publishing
        boolean transactional = Boolean.TRUE.equals(messageRequest.getTransactional());
        if (transactional) {
            kafkaTemplate.executeInTransaction(kt -> {
                kt.send(record).whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("[TX] Sent message=[{}] with offset=[{}]", value, result.getRecordMetadata().offset());
                    } else {
                        logger.error("[TX] Unable to send message=[{}] due to : {}", value, ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                });
                return null;
            });
        } else {
            kafkaTemplate.send(record).whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("Sent message=[{}] with offset=[{}]", value, result.getRecordMetadata().offset());
                } else {
                    logger.error("Unable to send message=[{}] due to : {}", value, ex.getMessage());
                }
            });
        }
    }

    // --- New Order Producer Method ---
    @Value("${kafka.topic.orders}")
    private String ordersTopic;

    @Value("${kafka.topic.rides}")
    private String ridesTopic;

    /**
     * Publish a ride event to the rides Kafka topic.
     * @param rideEvent the event to publish
     */
    public void sendRideEvent(com.example.kafka.model.RideEvent rideEvent) {
        String key = rideEvent.getId() != null ? rideEvent.getId().toString() : null;
        String value = gson.toJson(rideEvent);
        kafkaTemplate.send(ridesTopic, key, value).whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Ride event sent: {} (offset={})", value, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send ride event [{}] due to: {}", value, ex.getMessage());
            }
        });
    }

    public void sendOrder(OrderRequest orderRequest) {
    String key = orderRequest.getOrderId();
    String value = gson.toJson(orderRequest);
    kafkaTemplate.executeInTransaction(kt -> {
        kt.send(ordersTopic, key, value);
        logger.info("Order sent: {}", value);
        return null;
    });
}
}