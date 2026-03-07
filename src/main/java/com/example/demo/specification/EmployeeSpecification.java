package com.example.demo.specification;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entity.EmployeeEntity;

import jakarta.persistence.criteria.Predicate;

public class EmployeeSpecification {

    public static Specification<EmployeeEntity> filter(
            String name
//            String position,
//            Double minSalary,
//            Double maxSalary
    ) {

        return (root, query, cb) -> {

            ArrayList<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }

//            if (position != null) {
//                predicates.add(
//                        cb.like(
//                                cb.lower(root.get("position")),
//                                "%" + position.toLowerCase() + "%"
//                        )
//                );
//            }
//
//            if (minSalary != null) {
//                predicates.add(
//                        cb.greaterThanOrEqualTo(
//                                root.get("salary"), minSalary
//                        )
//                );
//            }
//
//            if (maxSalary != null) {
//                predicates.add(
//                        cb.lessThanOrEqualTo(
//                                root.get("salary"), maxSalary
//                        )
//                );
//            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}