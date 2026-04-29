package com.microservices.student.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String departmentName;  // e.g. Computer Science

    @Column(nullable = false, unique = true)
    private String departmentCode;  // e.g. CS

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Course> courses;
}
