package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import skillspace.skillspace_backend.Company.request.AddRecruiterDTO;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;

public interface CompanyWriteService {
    void updateCompanyProfile(UUID companyId, UpdateCompanyProfileDTO dto);
    void addRecruiter(UUID companyId, AddRecruiterDTO dto) throws AccessDeniedException;
}
