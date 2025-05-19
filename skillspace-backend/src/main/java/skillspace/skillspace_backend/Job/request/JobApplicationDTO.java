package skillspace.skillspace_backend.Job.request;

import java.util.UUID;

import skillspace.skillspace_backend.Company.response.CompanyApplicationDTO;

public record JobApplicationDTO(
    UUID id,
    String title,
    CompanyApplicationDTO company
) {
    
}
