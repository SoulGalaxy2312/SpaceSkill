package skillspace.skillspace_backend.Company.response;

import java.util.UUID;

public record CompanyApplicationDTO(
    UUID id,
    String companyName
) {
    
}
