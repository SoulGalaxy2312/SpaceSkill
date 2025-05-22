package skillspace.skillspace_backend.Company.response;

import java.util.UUID;

public record CompanyProfileDTO(
    UUID id,
    String profileName,
    String location,
    String about,
    boolean isCurrentCompany
) {
    
}
