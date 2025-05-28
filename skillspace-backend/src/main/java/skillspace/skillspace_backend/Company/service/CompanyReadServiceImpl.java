package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.mapper.CompanyMapper;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.mapper.BaseUserMapper;
import skillspace.skillspace_backend.shared.model.BaseUserBrief;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
public class CompanyReadServiceImpl implements CompanyReadService {
    private final CompanyHelper companyHelper;
    private final SecurityService securityService;

    public CompanyReadServiceImpl(CompanyHelper companyHelper, SecurityService securityService) {
        this.companyHelper = companyHelper;
        this.securityService = securityService;
    }

    public CompanyProfileDTO getCompanyProfile(UUID companyId) {
        boolean isCurrentCompany = securityService.assertCurrentUserMatches(companyId);
        Company company = companyHelper.getCompany(companyId);
        return CompanyMapper.toCompanyProfileDTO(company, isCurrentCompany);
    }

    public List<BaseUserBrief> getRecruiters(UUID companyId) {
        boolean isCurrentCompany = securityService.assertCurrentUserMatches(companyId);
        if (!isCurrentCompany) return null;
        Company company = companyHelper.getCompany(companyId);
        List<User> recruiters = company.getRecruiters();
        List<BaseUserBrief> response = recruiters.stream()
                                            .map(BaseUserMapper::toBaseUserBrief)
                                            .collect(Collectors.toList());
        return response;
    }
}
