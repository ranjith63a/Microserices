package com.microservices.student.event;


import java.time.LocalDateTime;

public class BaseEvent {
    private String eventType;
    private String source;
    private LocalDateTime timestamp;
}
