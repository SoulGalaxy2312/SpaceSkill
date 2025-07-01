package skillspace.skillspace_backend.Application.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import skillspace.skillspace_backend.Application.model.Application;

public class ApplicationSpecification {
    
    public static Specification<Application> ownedByUserWithId(UUID id) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("user").get("id"), id);
        };
    }

    public static Specification<Application> sortByAppliedAtDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("appliedAt")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Application> ownedByCompany(UUID id) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(
                root.get("job").get("company").get("id"), 
                id);
        };
    }
}
