package com.microservices.student.model;

import com.microservices.student.dto.DepartmentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
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

    public Department(DepartmentDTO request) {
        this.departmentCode = request.getDepartmentCode();
        this.departmentName = request.getDepartmentName();
    }
}
