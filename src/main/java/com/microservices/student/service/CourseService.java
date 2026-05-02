package com.microservices.student.service;

import com.microservices.student.dto.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {


    Long createCourse(CourseDTO request);

    Page<CourseDTO> getAllCourse(Long courseId, Long departmentId, Pageable pageable);

}
