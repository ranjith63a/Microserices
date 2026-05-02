package com.microservices.student.controller;

import com.microservices.student.dto.DepartmentDTO;
import com.microservices.student.service.impl.DepartmentServiceImpl;
import jakarta.validation.Valid;
import org.apache.catalina.authenticator.SavedRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/department")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Long> createCourse(@Valid @RequestBody DepartmentDTO request) {
        Long departmentId = departmentService.createDepartment(request);
        return ResponseEntity.ok(departmentId);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDTO>> getAllDepartment(@RequestParam(required = false) Long id,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String code,
                                                                @ParameterObject Pageable pageable) {
        Page<DepartmentDTO> departmentDTOS = departmentService.getAllDepartment(id, name, code, pageable);

        return ResponseEntity.ok(departmentDTOS);
    }
}
