package skillspace.skillspace_backend.User.response;

import java.util.UUID;

public record UserApplicationDTO(
    UUID id,
    String profileName
) {
    
}
