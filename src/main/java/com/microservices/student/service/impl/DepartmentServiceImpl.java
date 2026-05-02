package com.microservices.student.service.impl;

import com.microservices.student.dto.DepartmentDTO;
import com.microservices.student.model.Department;
import com.microservices.student.repository.DepartmentRepository;
import com.microservices.student.service.DepartmentService;
import com.microservices.student.specification.CourseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Long createDepartment(DepartmentDTO request) {

        Department newCourse = new Department(request);
        newCourse = departmentRepository.save(newCourse);
        return newCourse.getId();
    }

    @Override
    public Page<DepartmentDTO> getAllDepartment(Long departmentId, String departmentName,
                                                String code, Pageable pageable) {

        Specification<Department> specification = Specification
                .where(CourseSpecification.hasDeptId(departmentId))
                .and(CourseSpecification.hasDepartmentName(departmentName))
                .and(CourseSpecification.hasDepartmentCode(code));

        return departmentRepository.findAll(specification, pageable)
                .map(DepartmentDTO::new);
    }
}
