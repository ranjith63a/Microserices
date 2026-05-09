package com.microservices.department.service;

import com.microservices.department.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    Long createDepartment(DepartmentDTO request);

    Page<DepartmentDTO> getAllDepartment(Long departmentId, String departmentName, String code, Pageable pageable);

    DepartmentDTO getDepartmentByCode(String code);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();
}
