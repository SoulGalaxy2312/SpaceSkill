package skillspace.skillspace_backend.Admin.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillspace.skillspace_backend.shared.enums.UserRole;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminBaseUserBrief {
    UUID id;
    String email;
    String profileName;
    UserRole role;
}
