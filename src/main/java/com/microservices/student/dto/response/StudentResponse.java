package com.microservices.student.dto.response;

import com.microservices.student.model.Student;
import com.microservices.student.model.enums.StudentStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private Long id;
    private String studentCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private StudentStatus status;
    /*private Long departmentId;*/
    private DepartmentResponse department;  // from Feign call

    public StudentResponse(Student student, DepartmentResponse department) {
        this.id = student.getId();
        this.studentCode = student.getStudentCode();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.phoneNumber = student.getPhoneNumber();
        this.dateOfBirth = student.getDateOfBirth();
        this.department = department;
        this.status = student.getStatus();
    }
}