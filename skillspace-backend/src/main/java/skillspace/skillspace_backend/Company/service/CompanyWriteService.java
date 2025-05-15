package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;

public interface CompanyWriteService {
    void updateCompanyProfile(UUID companyId, UpdateCompanyProfileDTO dto);
}
