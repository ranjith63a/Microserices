package com.microservices.student.dto;

import com.microservices.student.Utill.StudentStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentDTO {

    private String firstName;

    private String lastName;

    private int age;

    @NotBlank
    private String studentCode;

    @Email
    private String email;

    private LocalDate dateOfBirth;

    private StudentStatus status;

    private String phoneNumber;

}
