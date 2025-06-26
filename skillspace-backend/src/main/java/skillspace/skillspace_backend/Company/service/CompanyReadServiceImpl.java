package skillspace.skillspace_backend.Company.service;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.mapper.BaseUserMapper;
import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.Company.mapper.CompanyMapper;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.shared.response.PagingDTO;
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

    public PagingDTO<BaseUserBrief> getRecruiters(int page, int size) {
        UUID companyId = securityService.getCurrentBaseUserId();
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = companyRepository.findRecruitersByCompanyId(companyId, pageable);

        return new PagingDTO<>(
            users.getContent().stream()
                .map(BaseUserMapper::toBaseUserBrief)
                .collect(Collectors.toList()),
            users.getNumber(),
            users.getSize(),
            users.getTotalElements(),
            users.getTotalPages()
        );
    }
}
