package com.microservices.student.client;

import com.microservices.student.dto.response.DepartmentResponse;
import com.microservices.student.fallback.DepartmentClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "department-service", fallbackFactory = DepartmentClientFallbackFactory.class)
public interface DepartmentClient {

    @GetMapping("/api/v1/department/{code}")
    DepartmentResponse getDepartmentByCode(@PathVariable String code);

    @GetMapping("/api/v1/department/id/{id}")
    DepartmentResponse getDepartmentById(@PathVariable Long id);

    @GetMapping("/api/v1/department/all")
    List<DepartmentResponse> getAllDepartments();
}