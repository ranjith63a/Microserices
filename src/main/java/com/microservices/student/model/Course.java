package com.microservices.student.model;

import com.microservices.student.Utill.CourseStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;


    @Column(nullable = false, unique = true)
    private String courseCode;

    private String description;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;  // ACTIVE, INACTIVE, COMPLETED

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
