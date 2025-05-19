package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;

import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.User.response.UserBriefDTO;

public interface CompanyReadService {
    CompanyProfileDTO getCompanyProfile(UUID companyId);
    List<UserBriefDTO> getRecruiters(UUID companyId);
}
