package com.microservices.department.service.impl;

import com.microservices.department.dto.CourseDTO;
import com.microservices.department.model.Course;
import com.microservices.department.model.Department;
import com.microservices.department.repository.CourseRepository;
import com.microservices.department.repository.DepartmentRepository;
import com.microservices.department.service.CourseService;
import com.microservices.department.specification.CourseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Long createCourse(CourseDTO request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Could not find the DepartmentId"));
        Course newCourse = new Course(request);
        newCourse.setDepartment(department);
        newCourse = courseRepository.save(newCourse);
        return newCourse.getId();
    }

    @Override
    public Page<CourseDTO> getAllCourse(Long courseId, Long departmentId, Pageable pageable) {

        Specification<Course> specification = Specification
                .where(CourseSpecification.hasCourseId(courseId))
                .and(CourseSpecification.hasDepartmentId(departmentId));

        return courseRepository.findAll(specification, pageable)
                .map(CourseDTO::new);
    }
}
