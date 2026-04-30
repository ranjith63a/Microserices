package com.microservices.student.specification;

import com.microservices.student.model.Student;
import com.microservices.student.model.StudentAddress;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<Student> hasFirstName(String firstName) {
        return (root, query, cb) ->
                (firstName == null || firstName.isEmpty())
                        ? null
                        : cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<StudentAddress> hasAddressId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<StudentAddress> hasState(String state) {
        return (root, query, cb) ->
                (state == null || state.isEmpty())
                        ? null
                        : cb.like(cb.lower(root.get("state")), "%" + state.toLowerCase() + "%");
    }

    public static Specification<StudentAddress> hasCity(String city) {
        return (root, query, cb) ->
                (city == null || city.isEmpty())
                        ? null
                        : cb.like(cb.lower(root.get("state")), "%" + city.toLowerCase() + "%");
    }
}
