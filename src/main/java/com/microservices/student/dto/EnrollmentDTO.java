package com.microservices.student.dto;

import com.microservices.student.model.Student;
import com.microservices.student.model.enums.EnrollmentStatus;
import com.microservices.student.model.enums.StudentStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class EnrollmentDTO {

    private Long id;

    @NotBlank(message = "Student Id is Required")
    private Long studentId;

    @NotBlank(message = "Department Code is Required")
    private String departmentCode;

    @NotBlank(message = "Enrollment Code is Required")
    private String enrollmentCode;

    @NotBlank(message = "Academic Year is Required")
    private LocalDate academicYear;

    @NotBlank(message = "Enrollment Status is Required")
    private EnrollmentStatus status;

}