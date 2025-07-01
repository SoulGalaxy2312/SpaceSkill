package skillspace.skillspace_backend.Application.service;

import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.shared.response.PagingDTO;

public interface ApplicationReadService {
    PagingDTO<ApplicationResponseDTO> retrieveApplications(int page, int size);
}