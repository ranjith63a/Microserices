package com.microservices.student.dto;

import com.microservices.student.Utill.StudentStatus;
import com.microservices.student.model.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {

    private Long id;
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


    public StudentDTO(Student student) {
        if (student == null) return;

        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.studentCode = student.getStudentCode();
        this.email = student.getEmail();
        this.dateOfBirth = student.getDateOfBirth();
        this.status = student.getStatus();
        this.phoneNumber = student.getPhoneNumber();
    }
}