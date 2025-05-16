package skillspace.skillspace_backend.Job.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record JobResponseDTO (
    UUID id,
    String title,
    Set<String> requiredSkills,
    String description,
    LocalDate createdAt
) {
    
}
