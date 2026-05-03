package com.microservices.student.dto.response;

import com.microservices.student.model.enums.EnrollmentStatus;
import com.microservices.student.model.enums.StudentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EnrollmentResponse {

    private Long id;
    private String enrollmentCode;
    private LocalDate academicYear;
    private EnrollmentStatus status;
    private StudentResponse student;
}