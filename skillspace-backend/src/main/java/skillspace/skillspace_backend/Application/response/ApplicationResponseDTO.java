package skillspace.skillspace_backend.Application.response;

import java.time.LocalDate;
import java.util.UUID;

import skillspace.skillspace_backend.Job.request.JobApplicationDTO;
import skillspace.skillspace_backend.User.response.UserApplicationDTO;

public record ApplicationResponseDTO(
    UUID id,
    JobApplicationDTO job,
    UserApplicationDTO user,
    LocalDate appliedAt,
    String resumeUrl
) {
    
}
