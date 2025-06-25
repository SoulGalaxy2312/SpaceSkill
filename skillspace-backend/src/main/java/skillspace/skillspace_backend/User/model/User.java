package skillspace.skillspace_backend.User.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.shared.enums.UserRole;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseUser {

    @ElementCollection
    @CollectionTable(
        name = "user_skills",
        joinColumns = @JoinColumn(name = "user_id")
    )
    private List<String> skills;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<Education> educations = new ArrayList<>();

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

    @ManyToMany(mappedBy = "recruiters")
    private List<Company> partnerCompanies = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_connections",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "connected_user_id")
    )
    private List<User> connections = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_following_companies",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> followingCompanies = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        setRole(UserRole.USER);
    }
}
