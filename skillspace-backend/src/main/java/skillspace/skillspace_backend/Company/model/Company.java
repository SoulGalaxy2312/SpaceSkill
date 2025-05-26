package skillspace.skillspace_backend.Company.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.User.model.User;
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
    private List<Job> jobs = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "company_recruiters",
        joinColumns = @JoinColumn(name = "company_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> recruiters = new ArrayList<>();

    @ManyToMany(mappedBy = "followingCompanies")
    private List<User> followers = new ArrayList<>();
}
