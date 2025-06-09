package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.mapper.CompanyMapper;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.request.AddRecruiterDTO;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.service.UserHelper;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class CompanyWriteServiceImpl implements CompanyWriteService {
    private final CompanyRepository companyRepository;
    private final CompanyHelper companyHelper;
    private final SecurityService securityService;
    private final UserHelper userHelper;

    public CompanyWriteServiceImpl(
        CompanyRepository companyRepository, 
        CompanyHelper companyHelper,
        SecurityService securityService,
        UserHelper userHelper) {

        this.companyHelper = companyHelper;
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.userHelper = userHelper;
    }

    public CompanyProfileDTO updateCompanyProfile(UUID companyId, UpdateCompanyProfileDTO dto) {
        boolean isCurrentCompany = securityService.assertCurrentUserMatches(companyId);
        if (!isCurrentCompany) {
            log.warn("Current user is not authorized to update company profile for company {}", companyId);
            throw new AccessDeniedException("You are not authorized to update this company's profile");
        };

        Company company = companyHelper.getCompany(companyId);
        if (dto.profileName() != null) company.setProfileName(dto.profileName());
        if (dto.location() != null) company.setLocation(dto.location());
        if (dto.about() != null) company.setAbout(dto.about());

        Company savedCompany = companyRepository.save(company);
        log.debug("Company profile updated successfully for company {}", companyId);
        log.debug("Returning updated company profile DTO");
        return CompanyMapper.toCompanyProfileDTO(savedCompany, isCurrentCompany);
    }

    public CompanyProfileDTO addRecruiter(UUID companyId, AddRecruiterDTO dto) throws AccessDeniedException {
        boolean isCurrentCompany = securityService.assertCurrentUserMatches(companyId);
        if (!isCurrentCompany) {
            log.warn("Current user is not authorized to add recruiter for company {}", companyId);
            throw new AccessDeniedException("You are not authorized to add recruiter for this company");
        };
        
        log.debug("Add recruiter method");
        Company company = securityService.getCurrentCompany();
        if (!companyId.equals(company.getId())) {
            log.warn("Current company is not authorized to add recruiter for this company");
            throw new AccessDeniedException("You are not authorized to add recruiter for this company");
        }

        User user = userHelper.getUserByEmail(dto.email());
        log.debug("User with id {} was successfully found", user.getId());

        List<User> recruiters = company.getRecruiters();
        if (recruiters.contains(user)) {
            log.info("User {} is already a recruiter for company {}", user.getId(), company.getId());
            return CompanyMapper.toCompanyProfileDTO(company, isCurrentCompany);
        }

        recruiters.add(user);
        company.setRecruiters(recruiters);
        Company savedCompany = companyRepository.save(company);
        log.debug("Recruiter with id {} added successfully to company {}", user.getId(), company.getId());
        log.debug("Returning updated company profile DTO");
        return CompanyMapper.toCompanyProfileDTO(savedCompany, isCurrentCompany);
    }
}
