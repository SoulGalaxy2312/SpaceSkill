package skillspace.skillspace_backend.Application.response;

import java.time.LocalDate;
import java.util.UUID;

import skillspace.skillspace_backend.Job.response.JobResponseDTO;

public record ApplicationResponseDTO(
    UUID id,
    JobResponseDTO job,
    LocalDate appliedAt,
    String resumeUrl
) {
    
}
