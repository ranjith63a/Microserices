package com.microservices.student.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentCreatedEvent extends BaseEvent{

    private Long studentId;
    private String name;
    private String email;
    private String departmentCode;
    private String eventType = "STUDENT_CREATED";
    private String source = "student-service";
}
