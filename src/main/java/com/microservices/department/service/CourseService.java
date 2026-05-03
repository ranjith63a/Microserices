package com.microservices.department.service;

import com.microservices.department.dto.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {


    Long createCourse(CourseDTO request);

    Page<CourseDTO> getAllCourse(Long courseId, Long departmentId, Pageable pageable);

}
