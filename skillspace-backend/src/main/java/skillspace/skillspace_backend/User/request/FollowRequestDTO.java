package skillspace.skillspace_backend.User.request;

import java.util.UUID;

import skillspace.skillspace_backend.shared.enums.UserRole;

public record FollowRequestDTO(
    UUID targetId,
    UserRole targetType
) {
    
}
