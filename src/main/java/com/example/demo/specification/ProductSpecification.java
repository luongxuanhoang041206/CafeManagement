package com.example.demo.specification;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import com.example.demo.entity.ProductEntity;
import jakarta.persistence.criteria.Predicate;
public class ProductSpecification {
    public static Specification<ProductEntity> filter(
            String name,
            Double minPrice,
            Double maxPrice,
            Boolean active,
            String groupId
    ) {
        return (root, query, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(
                    cb.like(cb.lower(root.get("name")),
                            "%" + name.toLowerCase() + "%")
                );
            }
            if (minPrice != null) {
                predicates.add(
                    cb.greaterThanOrEqualTo(root.get("price"), minPrice)
                );
            }
            if (maxPrice != null) {
                predicates.add(
                    cb.lessThanOrEqualTo(root.get("price"), maxPrice)
                );
            }
            if (active != null) {
                predicates.add(
                    cb.equal(root.get("active"), active)
                );
            }
            if (groupId != null) {
                predicates.add(
                    cb.equal(root.get("group").get("id"), groupId)
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}