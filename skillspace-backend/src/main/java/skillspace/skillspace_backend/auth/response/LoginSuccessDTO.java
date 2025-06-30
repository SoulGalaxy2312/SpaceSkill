package skillspace.skillspace_backend.auth.response;

import java.util.UUID;

import skillspace.skillspace_backend.shared.enums.UserRole;

public record LoginSuccessDTO(
    String jwtToken,
    UUID currentUserId,
    UserRole role
) {

}
