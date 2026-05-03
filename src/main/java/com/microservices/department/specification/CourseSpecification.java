package com.microservices.department.specification;

import com.microservices.department.model.Course;
import com.microservices.department.model.Department;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {

    public static Specification<Course> hasCourseId(Long id) {
        return ((root, query, criteriaBuilder) ->
                id == null ? null : criteriaBuilder.equal(root.get("id"), id));
    }

    public static Specification<Course> hasDepartmentId(Long departmentId) {
        return ((root, query, criteriaBuilder) ->
                departmentId == null ? null : criteriaBuilder.equal(root.get("department").get("id"), departmentId));
    }

    public static Specification<Department> hasDeptId(Long departmentId) {
        return ((root, query, criteriaBuilder) ->
                departmentId == null ? null : criteriaBuilder.equal(root.get("department").get("id"), departmentId));
    }


    public static Specification<Department> hasDepartmentName(String name) {
        return ((root, query, criteriaBuilder) ->
                (name == null || name.isEmpty()) ? null
                : criteriaBuilder.like(root.get("departmentName"), "%" + name + "%"));
    }

    public static Specification<Department> hasDepartmentCode(String code) {
        return ((root, query, criteriaBuilder) ->
                (code == null || code.isEmpty()) ? null
                : criteriaBuilder.like(root.get("departmentCode"), "%" + code + "%"));
    }
}
