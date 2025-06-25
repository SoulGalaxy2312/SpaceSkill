package skillspace.skillspace_backend.Application.response;

import java.time.LocalDate;
import java.util.UUID;

import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.Job.request.JobApplicationDTO;
import skillspace.skillspace_backend.shared.enums.ApplicationStatus;

public record ApplicationResponseDTO(
    UUID id,
    JobApplicationDTO job,
    BaseUserBrief user,
    LocalDate appliedAt,
    String resumeUrl,
    ApplicationStatus status,
    String reviewerNote
) {
    
}
