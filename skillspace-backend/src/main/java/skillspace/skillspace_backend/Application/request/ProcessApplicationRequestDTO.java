package skillspace.skillspace_backend.Application.request;

import skillspace.skillspace_backend.shared.enums.ApplicationStatus;

public record ProcessApplicationRequestDTO(
    ApplicationStatus status,
    String reviewerNote
) {
    
}
