package com.microservices.student.model;

import com.microservices.student.common.model.BaseEntity;
import com.microservices.student.dto.StudentAddressDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students_address")
@Getter
@Setter
@NoArgsConstructor
public class StudentAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long pinCode;

    public StudentAddress(StudentAddressDTO request) {
        this.city = request.getCity();
        this.state = request.getState();
        this.address = request.getAddress();
        this.pinCode = request.getPinCode();
    }
}
