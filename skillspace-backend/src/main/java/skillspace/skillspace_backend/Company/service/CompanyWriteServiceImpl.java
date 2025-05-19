package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.request.AddRecruiterDTO;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;
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

    public void updateCompanyProfile(UUID companyId, UpdateCompanyProfileDTO dto) {
        Company company = companyHelper.getCompany(companyId);

        if (dto.profileName() != null) company.setProfileName(dto.profileName());
        if (dto.location() != null) company.setLocation(dto.location());
        if (dto.about() != null) company.setAbout(dto.about());

        companyRepository.save(company);
    }

    public void addRecruiter(UUID companyId, AddRecruiterDTO dto) throws AccessDeniedException {
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
            return;
        }

        boolean added = recruiters.add(user);
        if (added) {
            company.setRecruiters(recruiters);
            companyRepository.save(company);
        }
    }
}
