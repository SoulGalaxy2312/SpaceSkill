package skillspace.skillspace_backend.BaseUser.response;

import java.util.UUID;

import skillspace.skillspace_backend.shared.enums.UserRole;

public record BaseUserProfileDTO(
    UUID id,
    String profileName,
    String location,
    String about,
    UserRole role,
    boolean isCurrentBaseUser,
    boolean isFollowedByCurrentBaseUser
) {
}