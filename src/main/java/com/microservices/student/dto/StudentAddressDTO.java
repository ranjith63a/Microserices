package com.microservices.student.dto;

import com.microservices.student.model.StudentAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class StudentAddressDTO {

    private Long id;

    @NotBlank(message = "Student Id is required")
    private Long studentId;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Pin code is required")
    private Long pinCode;

    public StudentAddressDTO(StudentAddress studentAddress) {
        this.id = studentAddress.getId();
        this.studentId = studentAddress.getStudent().getId();
        this.city = studentAddress.getCity();
        this.state = studentAddress.getState();
        this.address = studentAddress.getAddress();
        this.pinCode = studentAddress.getPinCode();
    }
}
