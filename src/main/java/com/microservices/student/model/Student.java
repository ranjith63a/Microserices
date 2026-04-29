package com.microservices.student.model;

import com.microservices.student.Utill.StudentStatus;
import com.microservices.student.common.model.BaseEntity;
import com.microservices.student.dto.StudentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String studentCode;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;

    @Column(nullable = false)
    private String phoneNumber;


    public Student(String firstName, String lastName, int age, String studentCode, String email,
                   LocalDate dateOfBirth, StudentStatus status, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.studentCode = studentCode;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.phoneNumber = phoneNumber;
    }

    public Student(StudentDTO request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.age = request.getAge();
        this.studentCode = request.getStudentCode();
        this.email = request.getEmail();
        this.dateOfBirth = request.getDateOfBirth();
        this.status = request.getStatus();
        this.phoneNumber = request.getPhoneNumber();
    }
}
