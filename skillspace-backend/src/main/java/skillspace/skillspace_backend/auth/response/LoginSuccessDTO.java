package skillspace.skillspace_backend.auth.response;

import java.util.UUID;

public record LoginSuccessDTO(
    String jwtToken,
    UUID currentUserId
) {

}
