package com.example.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private String message;
    private String messageType;
    private String id;
    private String user;
    private Map<String, String> headers;
    private Long timestamp;
    private Boolean transactional;
}

