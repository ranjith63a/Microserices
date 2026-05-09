package com.microservices.student.repository;

import com.microservices.student.model.Enrollment;
import com.microservices.student.model.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStatus(EnrollmentStatus enrollmentStatus);
}
