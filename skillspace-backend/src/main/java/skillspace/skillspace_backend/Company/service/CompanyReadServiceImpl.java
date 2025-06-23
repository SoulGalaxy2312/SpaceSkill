package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.mapper.CompanyMapper;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.shared.mapper.BaseUserMapper;
import skillspace.skillspace_backend.shared.model.BaseUserBrief;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class CompanyReadServiceImpl implements CompanyReadService {
    private final CompanyRepository companyRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public CompanyReadServiceImpl(CompanyRepository companyRepository, SecurityService securityService, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    public CompanyProfileDTO getCompanyProfile(UUID companyId) {
        Company company = companyRepository.getCompanyByIdOrThrow(companyId);
        UUID currentBaseUserId = securityService.getCurrentBaseUserId();

        boolean isCurrentCompany = (currentBaseUserId != null) 
            && company.getId().equals(currentBaseUserId);
        boolean isFollowedByCurrentBaseUser = (!isCurrentCompany) 
            && userRepository.isCompanyFollowedByCurrentBaseUser(companyId, currentBaseUserId);
        
        return CompanyMapper.toCompanyProfileDTO(company, isCurrentCompany, isFollowedByCurrentBaseUser);
    }

    public List<BaseUserBrief> getRecruiters(UUID companyId, int page, int size) throws AccessDeniedException {
        boolean isCurrentCompany = securityService.assertCurrentUserMatches(companyId);
        if (!isCurrentCompany) {
            log.warn("Current user is not authorized to view recruiters for company {}", companyId);
            throw new AccessDeniedException("You are not authorized to view recruiters for this company");
        };

        Company company = companyRepository.getCompanyByIdOrThrow(companyId);
        List<User> recruiters = company.getRecruiters();
        List<BaseUserBrief> allBriefs = recruiters.stream()
                                            .map(BaseUserMapper::toBaseUserBrief)
                                            .collect(Collectors.toList());

        int fromIndex = Math.max(0, page * size);
        int toIndex = Math.min(fromIndex + size, allBriefs.size());
        if (fromIndex >= allBriefs.size()) {
            return List.of(); 
        };
        
        return allBriefs.subList(fromIndex, toIndex);
    }
}
