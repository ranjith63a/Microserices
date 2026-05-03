package com.microservices.department.dto;

import com.microservices.department.Utill.CourseStatus;
import com.microservices.department.model.Course;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course Name is Required")
    private String courseName;

    @NotBlank(message = "Course Code is Required")
    private String courseCode;

    private String description;

    @NotBlank(message = "Status is Required")
    private CourseStatus status;

    @NotBlank(message = "Department is Required")
    private Long departmentId;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.courseName = course.getCourseName();
        this.courseCode = course.getCourseCode();
        this.description = course.getDescription();
        this.status = course.getStatus();
        this.departmentId = course.getDepartment().getId();
    }
}
