package skillspace.skillspace_backend.User.response;

import java.util.UUID;

public record UserBriefDTO(
    UUID id,
    String profileName
) {
    
}
