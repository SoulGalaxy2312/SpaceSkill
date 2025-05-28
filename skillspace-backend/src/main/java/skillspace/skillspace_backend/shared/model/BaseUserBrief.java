package skillspace.skillspace_backend.shared.model;

import java.util.UUID;

import skillspace.skillspace_backend.shared.enums.UserRole;

public record BaseUserBrief(
    UUID id,
    String profileName,
    UserRole role
) {
}