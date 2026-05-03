package com.microservices.department.model;

import com.microservices.department.Utill.CourseStatus;
import com.microservices.department.common.model.BaseEntity;
import com.microservices.department.dto.CourseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course extends BaseEntity {

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

    public Course(CourseDTO request) {
        this.courseCode = request.getCourseCode();
        this.courseName = request.getCourseName();
        this.status = request.getStatus();
        this.description = request.getDescription();
    }
}
