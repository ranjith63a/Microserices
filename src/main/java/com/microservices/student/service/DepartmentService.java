package com.microservices.student.service;

import com.microservices.student.dto.DepartmentDTO;
import com.microservices.student.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {

    Long createDepartment(DepartmentDTO request);

    Page<DepartmentDTO> getAllDepartment(Long departmentId, String departmentName, String code, Pageable pageable);
}
