package skillspace.skillspace_backend.Job.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;

public record JobResponseDTO (
    UUID id,
    BaseUserBrief company,
    String location,
    String title,
    Set<String> requiredSkills,
    String description,
    LocalDate createdAt
) {
    
}
