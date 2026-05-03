package com.microservices.student.producer;

import com.microservices.student.event.StudentCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    public void sendStudentCreatedEvent(StudentCreatedEvent event) {
        kafkaTemplate.send("student.created", event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Event sent successfully: " + event);
                    } else {
                        System.err.println("Failed to send event: " + ex.getMessage());
                    }
                });
    }
}