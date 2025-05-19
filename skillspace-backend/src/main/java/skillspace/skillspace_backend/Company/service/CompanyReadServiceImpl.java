package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.mapper.CompanyMapper;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.User.mapper.UserMapper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.response.UserBriefDTO;

@Service
public class CompanyReadServiceImpl implements CompanyReadService {
    private final CompanyHelper companyHelper;

    public CompanyReadServiceImpl(CompanyHelper companyHelper) {
        this.companyHelper = companyHelper;
    }

    public CompanyProfileDTO getCompanyProfile(UUID companyId) {
        Company company = companyHelper.getCompany(companyId);
        return CompanyMapper.toCompanyProfileDTO(company);
    }

    public List<UserBriefDTO> getRecruiters(UUID companyId) {
        Company company = companyHelper.getCompany(companyId);
        List<User> recruiters = company.getRecruiters();
        List<UserBriefDTO> response = recruiters.stream()
                                            .map(UserMapper::toUserBriefDTO)
                                            .collect(Collectors.toList());
        return response;
    }
}
