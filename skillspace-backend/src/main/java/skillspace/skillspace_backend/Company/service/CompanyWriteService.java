package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import skillspace.skillspace_backend.Company.request.AddRecruiterDTO;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;

public interface CompanyWriteService {
    CompanyProfileDTO updateCompanyProfile(UUID companyId, UpdateCompanyProfileDTO dto);
    CompanyProfileDTO addRecruiter(UUID companyId, AddRecruiterDTO dto) throws AccessDeniedException;
}
