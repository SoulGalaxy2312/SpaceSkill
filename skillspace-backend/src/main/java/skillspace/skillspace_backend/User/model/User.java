package skillspace.skillspace_backend.User.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.model.BaseUser;

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
    private Set<String> skills;

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

    @OneToMany(mappedBy = "user")
    private Set<Application> applications = new HashSet<>();

    @PrePersist
    public void prePersist() {
        setRole(UserRole.USER);
    }
}
