package com.microservices.student.controller;

import com.microservices.student.dto.EnrollmentDTO;
import com.microservices.student.dto.StudentAddressDTO;
import com.microservices.student.dto.StudentDTO;
import com.microservices.student.dto.response.EnrollmentResponse;
import com.microservices.student.service.impl.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    StudentServiceImpl studentService;

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody StudentDTO request) {
        Long studentId = studentService.createStudent(request);
        return ResponseEntity.ok(studentId);
    }

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> findAllStudents(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) String firstName,
                                                            @ParameterObject Pageable pageable) {
        Page<StudentDTO> students = studentService.findAllStudents(id, firstName, pageable);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/address")
    public ResponseEntity<Long> createAddress(@Valid @RequestBody StudentAddressDTO request) {
        Long studentAddressId = studentService.createStudentAddress(request);
        return ResponseEntity.ok(studentAddressId);
    }



    @GetMapping("/studentAddress")
    public ResponseEntity<Page<StudentAddressDTO>> findAllStudentAddress(@RequestParam(required = false) Long id,
                                                                         @RequestParam(required = false) String state,
                                                                         @RequestParam(required = false) String city,
                                                            @ParameterObject Pageable pageable) {
        Page<StudentAddressDTO> studentAddress = studentService.findAllStudentAddress(id, state, city, pageable);
        return ResponseEntity.ok(studentAddress);
    }

    @PostMapping("enrollment")
    public ResponseEntity<EnrollmentResponse> createEnrollment(@Valid @RequestBody EnrollmentDTO request) {
        EnrollmentResponse response = studentService.createEnrolment(request);
        return ResponseEntity.ok(response);
    }
}
