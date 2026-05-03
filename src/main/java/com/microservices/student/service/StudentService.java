package com.microservices.student.service;

import com.microservices.student.dto.EnrollmentDTO;
import com.microservices.student.dto.StudentAddressDTO;
import com.microservices.student.dto.StudentDTO;
import com.microservices.student.dto.response.EnrollmentResponse;
import com.microservices.student.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {


    Long createStudent(StudentDTO request);

    Long createStudentAddress(StudentAddressDTO request);

    Page<StudentDTO> findAllStudents(Long id, String studentName, Pageable pageable);

    Page<StudentAddressDTO> findAllStudentAddress(Long id, String state, String city, Pageable pageable);

    EnrollmentResponse createEnrolment(EnrollmentDTO enrollmentDTO);
}
