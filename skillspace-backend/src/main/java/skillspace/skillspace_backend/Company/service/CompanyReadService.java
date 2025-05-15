package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;

public interface CompanyReadService {
    CompanyProfileDTO getCompanyProfile(UUID companyId);
}
