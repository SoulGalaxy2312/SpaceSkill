package skillspace.skillspace_backend.Application.service.retrievingApplication;

import java.util.UUID;

import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.response.PagingDTO;

public interface RetrievingStrategy {
    PagingDTO<ApplicationResponseDTO> retrieveApplications(UUID id, int page, int size);
    UserRole getTargetUserRole();
}