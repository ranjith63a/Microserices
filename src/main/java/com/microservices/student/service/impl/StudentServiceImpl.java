package com.microservices.student.service.impl;

import com.microservices.student.dto.*;
import com.microservices.student.dto.response.DepartmentResponse;
import com.microservices.student.dto.response.EnrollmentResponse;
import com.microservices.student.dto.response.StudentResponse;
import com.microservices.student.event.StudentCreatedEvent;
import com.microservices.student.exception.InvalidDepartmentException;
import com.microservices.student.model.Enrollment;
import com.microservices.student.model.Student;
import com.microservices.student.model.StudentAddress;
import com.microservices.student.model.enums.EnrollmentStatus;
import com.microservices.student.producer.EventProducer;
import com.microservices.student.repository.EnrollmentRepository;
import com.microservices.student.repository.StudentAddressRepository;
import com.microservices.student.repository.StudentRepository;
import com.microservices.student.client.DepartmentClient;
import com.microservices.student.service.DepartmentCacheService;
import com.microservices.student.service.StudentService;
import com.microservices.student.specification.StudentSpecification;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    private final DepartmentClient departmentClient;

    @Autowired
    private final StudentAddressRepository addressRepository;

    @Autowired
    private final EventProducer eventProducer;

    @Autowired
    private final DepartmentCacheService cacheService;

    public StudentServiceImpl(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository, DepartmentClient departmentClient, StudentAddressRepository addressRepository, EventProducer eventProducer, DepartmentCacheService cacheService) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.departmentClient = departmentClient;
        this.addressRepository = addressRepository;
        this.eventProducer = eventProducer;
        this.cacheService = cacheService;
    }

    @Override
    public Long createStudent(StudentDTO request) {

        Student newStudent = new Student(request);
        newStudent = studentRepository.save(newStudent);
        return newStudent.getId();
    }

    @Override
    public Long createStudentAddress(StudentAddressDTO request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(()-> new RuntimeException("Could not find the Student Id : " + request.getStudentId()));

        StudentAddress address = new StudentAddress(request);
        address.setStudent(student);
        address = addressRepository.save(address);

        return address.getId();
    }

    @Override
    public Page<StudentDTO> findAllStudents(Long id, String firstName, Pageable pageable) {

        Specification<Student> spec = Specification
                .where(StudentSpecification.hasId(id))
                .and(StudentSpecification.hasFirstName(firstName));

        return studentRepository.findAll(spec, pageable)
                .map(StudentDTO::new);
    }

    @Override
    public Page<StudentAddressDTO> findAllStudentAddress(Long id, String state, String city, Pageable pageable) {

        Specification<StudentAddress> spec = Specification
                .where(StudentSpecification.hasAddressId(id))
                .and(StudentSpecification.hasState(state))
                .and(StudentSpecification.hasCity(city));

        return addressRepository.findAll(spec, pageable)
                .map(StudentAddressDTO::new);
    }


    @Override
    public EnrollmentResponse createEnrolment(EnrollmentDTO request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        DepartmentResponse department = departmentClient.getDepartmentByCode(request.getDepartmentCode());

        boolean isServiceUp = !"NA".equals(department.getDepartmentCode());

        boolean saveWithVerified = false;
        Long departmentId = department.getId();
        if (isServiceUp) {
            // ✅ Service UP — validated live
            log.info("Department service UP — live validation passed.");
            saveWithVerified = true;

        } else {
            // ⚠️ Service DOWN — fallback triggered
            log.warn("Department service DOWN — checking Redis cache...");

            if (!cacheService.isCacheAvailable()) {
                // No cache at all — can't validate, reject request
                throw new ServiceUnavailableException(
                        "Department service is unavailable and no cache exists. Try again later."
                );
            }

            if (!cacheService.isValidCode(request.getDepartmentCode())) {
                // Cache available but code not found — invalid code
                throw new InvalidDepartmentException(
                        "Department code '" + request.getDepartmentCode() + "' does not exist."
                );
            }

            // Code found in cache — save as PENDING
            department = cacheService.getDepartmentFromCache(request.getDepartmentCode());
            departmentId = department.getId();
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentCode(request.getEnrollmentCode());
        //enrollment.setStatus(request.getStatus());
        enrollment.setAcademicYear(request.getAcademicYear());
        enrollment.setStudent(student);
        enrollment.setDepartmentId(departmentId);

        if (saveWithVerified) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        } else {
            enrollment.setStatus(EnrollmentStatus.ENROLLED);
        }
        enrollment = enrollmentRepository.save(enrollment);

        StudentResponse studentResponse = new StudentResponse(student, department);

        // Send Mail After Student created
        StudentCreatedEvent event = new StudentCreatedEvent();
        event.setStudentId(student.getId());
        event.setName(student.getFirstName() + " " + student.getLastName());
        event.setEmail(student.getEmail());
        event.setDepartmentCode(studentResponse.getDepartment().getDepartmentCode());

        eventProducer.sendStudentCreatedEvent(event);

        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .enrollmentCode(enrollment.getEnrollmentCode())
                .academicYear(enrollment.getAcademicYear())
                .status(enrollment.getStatus())
                .student(studentResponse)
                .build();
    }

}
