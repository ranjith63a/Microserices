package com.microservices.department.dto;

import com.microservices.department.model.Department;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepartmentDTO {

    private Long id;

    @NotBlank(message = "Department Name is Required")
    private String departmentName;

    @NotBlank(message = "Department Code Required")
    private String departmentCode;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.departmentCode = department.getDepartmentCode();
        this.departmentName = department.getDepartmentName();
    }
}
