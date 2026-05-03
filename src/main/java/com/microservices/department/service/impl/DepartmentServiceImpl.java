package com.microservices.department.service.impl;

import com.microservices.department.dto.DepartmentDTO;
import com.microservices.department.model.Department;
import com.microservices.department.repository.DepartmentRepository;
import com.microservices.department.service.DepartmentService;
import com.microservices.department.specification.CourseSpecification;
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


    @Override
    public DepartmentDTO getDepartmentByCode(String code) {

        Department department = departmentRepository.findByDepartmentCode(code)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return new DepartmentDTO(department);
    }
}
