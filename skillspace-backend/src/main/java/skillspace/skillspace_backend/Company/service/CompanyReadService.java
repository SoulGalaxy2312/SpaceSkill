package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.shared.response.PagingDTO;

public interface CompanyReadService {
    CompanyProfileDTO getCompanyProfile(UUID companyId);
    PagingDTO<BaseUserBrief> getRecruiters(int page, int size);
}
