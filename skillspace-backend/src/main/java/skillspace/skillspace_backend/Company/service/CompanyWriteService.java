package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface CompanyWriteService {
    StatusResponseDTO updateCompanyProfile(UpdateCompanyProfileDTO dto);
    StatusResponseDTO addRecruiter(UUID recruiterId);
    StatusResponseDTO removeRecruiter(UUID recruiterId);
}
