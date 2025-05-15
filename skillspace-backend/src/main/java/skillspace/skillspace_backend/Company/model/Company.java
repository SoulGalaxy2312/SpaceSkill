package skillspace.skillspace_backend.Company.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
}
