package com.microservices.student.service;

import com.microservices.student.dto.StudentAddressDTO;
import com.microservices.student.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {


    Long createStudent(StudentDTO request);

    Long createStudentAddress(StudentAddressDTO request);

    Page<StudentDTO> findAllStudents(Long id, String studentName, Pageable pageable);

    Page<StudentAddressDTO> findAllStudentAddress(Long id, String state, String city, Pageable pageable);
}
