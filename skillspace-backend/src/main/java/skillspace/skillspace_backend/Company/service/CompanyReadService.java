package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;

import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;

public interface CompanyReadService {
    CompanyProfileDTO getCompanyProfile(UUID companyId);
    List<BaseUserBrief> getRecruiters(int page, int size);
}
