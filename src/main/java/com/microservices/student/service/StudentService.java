package com.microservices.student.service;

import com.microservices.student.dto.StudentDTO;

public interface StudentService {


    Long createStudent(StudentDTO request);

}
