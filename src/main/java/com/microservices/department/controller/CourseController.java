package com.microservices.department.controller;

import com.microservices.department.dto.CourseDTO;
import com.microservices.department.service.impl.CourseServiceImpl;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

    private final CourseServiceImpl courseService;

    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Long> createCourse(@Valid @RequestBody CourseDTO request) {
        Long courseId = courseService.createCourse(request);
        return ResponseEntity.ok(courseId);
    }

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> fineAllCourse(@RequestParam(required = false) Long courseId,
                                                         @RequestParam(required = false) Long departmentId,
                                                         @ParameterObject Pageable pageable) {
        Page<CourseDTO> courseDTOS = courseService.getAllCourse(courseId, departmentId, pageable);
        return ResponseEntity.ok(courseDTOS);
    }
}
