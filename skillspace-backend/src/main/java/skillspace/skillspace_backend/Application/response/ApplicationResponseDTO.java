package skillspace.skillspace_backend.Application.response;

import java.time.LocalDate;
import java.util.UUID;

import skillspace.skillspace_backend.Job.request.JobApplicationDTO;
import skillspace.skillspace_backend.User.response.UserBriefDTO;

public record ApplicationResponseDTO(
    UUID id,
    JobApplicationDTO job,
    UserBriefDTO user,
    LocalDate appliedAt,
    String resumeUrl
) {
    
}
