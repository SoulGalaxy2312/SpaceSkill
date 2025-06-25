package skillspace.skillspace_backend.Admin.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends BaseUser {
}
