package com.microservices.student.controller;

import com.microservices.student.dto.StudentDTO;
import com.microservices.student.model.Student;
import com.microservices.student.service.StudentService;
import com.microservices.student.service.impl.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
