package skillspace.skillspace_backend.User.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.model.BaseUser;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseUser {

    private String about;

    @ElementCollection
    private List<String> skills;

    @ElementCollection
    @CollectionTable(
        name = "user_experience", 
        joinColumns = @JoinColumn(name = "user_id")
    )
    private List<String> experience = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "user_education",
        joinColumns = @JoinColumn(name = "user_id")
    )
    private List<String> educations = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        setRole(UserRole.USER);
    }

    private String firstName;

    private String lastName;
}
