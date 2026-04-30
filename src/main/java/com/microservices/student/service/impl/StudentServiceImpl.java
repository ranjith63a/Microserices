package com.microservices.student.service.impl;

import com.microservices.student.dto.StudentAddressDTO;
import com.microservices.student.dto.StudentDTO;
import com.microservices.student.model.Student;
import com.microservices.student.model.StudentAddress;
import com.microservices.student.repository.StudentAddressRepository;
import com.microservices.student.repository.StudentRepository;
import com.microservices.student.service.StudentService;
import com.microservices.student.specification.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final StudentAddressRepository addressRepository;

    public StudentServiceImpl(StudentRepository studentRepository, StudentAddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
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

}
