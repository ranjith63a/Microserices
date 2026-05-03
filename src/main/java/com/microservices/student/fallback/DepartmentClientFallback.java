package com.microservices.student.fallback;

import com.microservices.student.dto.response.DepartmentResponse;
import com.microservices.student.client.DepartmentClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// Fallback class
@Component
@Slf4j
public class DepartmentClientFallback implements DepartmentClient {

    @Override
    public DepartmentResponse getDepartmentById(String code) {
        log.warn("Department service down");
        throw new RuntimeException("Department service unavailable");
    }
}