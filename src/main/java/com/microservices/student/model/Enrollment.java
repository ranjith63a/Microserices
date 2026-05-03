package com.microservices.student.model;

import com.microservices.student.common.model.BaseEntity;
import com.microservices.student.model.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
public class Enrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String enrollmentCode;        // e.g. ENR-2024-001

    // Relation to Student (same database)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Department from department-service (no JPA join)
    @Column(nullable = false)
    private Long departmentId;

    @Column(nullable = false)
    private LocalDate academicYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;
}
