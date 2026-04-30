package com.microservices.student.repository;

import com.microservices.student.model.Student;
import com.microservices.student.model.StudentAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAddressRepository extends JpaRepository<StudentAddress, Long>, JpaSpecificationExecutor<StudentAddress> {
}
