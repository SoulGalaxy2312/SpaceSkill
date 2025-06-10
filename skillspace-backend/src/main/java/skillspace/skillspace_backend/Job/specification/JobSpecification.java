package skillspace.skillspace_backend.Job.specification;

import org.springframework.data.jpa.domain.Specification;

import skillspace.skillspace_backend.Job.model.Job;

public class JobSpecification {
    public static Specification<Job> hasRequiredSkill(String skill) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.isMember(skill, root.get("requiredSkills"));
        };
    }

    public static Specification<Job> fromCompany(String companyName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("company").get("profileName"), companyName);
        };
    }

    public static Specification<Job>  sortByCreatedAtDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return criteriaBuilder.conjunction(); // Always true
        };
    }

    public static Specification<Job> fullTextTitleSearch(String keyword) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<Job> isLocatedAt(String location) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%");
        };
    }
}
