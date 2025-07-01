package skillspace.skillspace_backend.Application.service;

import java.util.UUID;

import skillspace.skillspace_backend.Application.exception.ApplicationNotFoundException;
import skillspace.skillspace_backend.Application.request.ProcessApplicationRequestDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface ApplicationWriteService {
    StatusResponseDTO applyJob(UUID jobId, String personalStatement);
    StatusResponseDTO processApplication(UUID applicationId, ProcessApplicationRequestDTO dto) throws ApplicationNotFoundException;
}
