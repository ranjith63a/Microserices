package com.microservices.student.client;

import com.microservices.student.dto.response.DepartmentResponse;
import com.microservices.student.fallback.DepartmentClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service", fallback = DepartmentClientFallback.class)
public interface DepartmentClient {

    @GetMapping("/api/v1/department/{code}")
    DepartmentResponse getDepartmentById(@PathVariable String code);
}