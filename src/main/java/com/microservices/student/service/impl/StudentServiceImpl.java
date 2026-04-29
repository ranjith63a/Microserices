package com.microservices.student.service.impl;

import com.microservices.student.dto.StudentDTO;
import com.microservices.student.model.Student;
import com.microservices.student.repository.StudentRepository;
import com.microservices.student.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Long createStudent(StudentDTO request) {

        Student newStudent = new Student(request);
        newStudent = studentRepository.save(newStudent);
        return newStudent.getId();
    }
}
