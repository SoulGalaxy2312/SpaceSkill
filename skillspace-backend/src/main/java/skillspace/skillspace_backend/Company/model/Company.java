package skillspace.skillspace_backend.Company.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.model.BaseUser;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "companies")
public class Company extends BaseUser {
    @PrePersist
    public void prePersist() {
        setRole(UserRole.COMPANY);
    }

    @OneToMany(
        mappedBy = "company",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private Set<Job> jobs = new HashSet<>();
}
