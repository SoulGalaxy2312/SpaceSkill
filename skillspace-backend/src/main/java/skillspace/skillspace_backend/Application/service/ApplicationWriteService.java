package skillspace.skillspace_backend.Application.service;

import java.util.UUID;

import skillspace.skillspace_backend.Application.exception.ApplicationNotFoundException;
import skillspace.skillspace_backend.Application.request.ApplicationRequestDTO;
import skillspace.skillspace_backend.Application.request.ProcessApplicationRequestDTO;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;

public interface ApplicationWriteService {
    ApplicationResponseDTO applyJob(UUID jobId, ApplicationRequestDTO dto);
    ApplicationResponseDTO processApplication(UUID applicationId, ProcessApplicationRequestDTO dto) throws ApplicationNotFoundException;
}
