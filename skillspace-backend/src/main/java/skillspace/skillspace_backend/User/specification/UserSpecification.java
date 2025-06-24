package skillspace.skillspace_backend.User.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import skillspace.skillspace_backend.User.model.User;

public class UserSpecification {
    public static Specification<User> fullTextSearchProfileName(String keyword) {
        return (root, query, cb) -> {
                Path<String> path = root.get("profileName");
                Predicate predicate = cb.like(cb.lower(path), keyword.toLowerCase() + "%");
                System.out.println("Generated LIKE: " + keyword.toLowerCase() + "%");
                return predicate;
            };
        };
}
