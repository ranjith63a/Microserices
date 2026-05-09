package com.microservices.student.fallback;

import com.microservices.student.dto.response.DepartmentResponse;
import com.microservices.student.client.DepartmentClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/*
// Fallback class
@Component
@Slf4j
public class DepartmentClientFallback implements DepartmentClient {

    @Override
    public DepartmentResponse getDepartmentByCode(String code) {
        log.warn("Department service down");
        return new DepartmentResponse(
                0L,
                "NA",
                "Department Unavailable"
        );
    }
}*/

// FallbackFactory — gives you the actual exception
@Component
@Slf4j
public class DepartmentClientFallbackFactory
        implements FallbackFactory<DepartmentClient> {

    @Override
    public DepartmentClient create(Throwable cause) {
        log.warn("Department service down. Reason: {}", cause.getMessage());

        return new DepartmentClient() {
            @Override
            public DepartmentResponse getDepartmentByCode(String code) {
                return new DepartmentResponse(0L, "NA", "Department Unavailable");
            }

            @Override
            public DepartmentResponse getDepartmentById(Long id) {
                return new DepartmentResponse(0L, "NA", "Department Unavailable");
            }

            @Override
            public List<DepartmentResponse> getAllDepartments() {
                return Collections.emptyList();
            }
        };
    }
}