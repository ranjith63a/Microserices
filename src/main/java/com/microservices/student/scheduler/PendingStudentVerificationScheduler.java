package com.microservices.student.scheduler;

import com.microservices.student.client.DepartmentClient;
import com.microservices.student.dto.response.DepartmentResponse;
import com.microservices.student.model.Enrollment;
import com.microservices.student.model.enums.EnrollmentStatus;
import com.microservices.student.repository.EnrollmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PendingStudentVerificationScheduler {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private DepartmentClient departmentClient;

    // Run every 1 minute — retry PENDING students
    @Scheduled(fixedDelay = 60000)
    public void verifyPendingStudents() {
        List<Enrollment> pendingEnrollment = enrollmentRepository
                .findByStatus(EnrollmentStatus.ENROLLED);

        if (pendingEnrollment.isEmpty()) return;

        log.info("Verifying {} pending students...", pendingEnrollment.size());

        for (Enrollment enrollment : pendingEnrollment) {
            DepartmentResponse dept = departmentClient
                    .getDepartmentById(enrollment.getDepartmentId());

            if (!"NA".equals(dept.getDepartmentCode())) {
                // Department service back UP — now verified
                enrollment.setStatus(EnrollmentStatus.COMPLETED);
                enrollmentRepository.save(enrollment);
                log.info("Student {} verified successfully.", enrollment.getId());
            } else {
                log.warn("Student {} still pending — service still down.", enrollment.getId());
            }
        }
    }
}
