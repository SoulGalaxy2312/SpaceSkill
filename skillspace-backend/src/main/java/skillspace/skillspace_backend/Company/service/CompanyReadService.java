package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.shared.model.BaseUserBrief;

public interface CompanyReadService {
    CompanyProfileDTO getCompanyProfile(UUID companyId);
    List<BaseUserBrief> getRecruiters(UUID companyId, int page, int size) throws AccessDeniedException;
}
