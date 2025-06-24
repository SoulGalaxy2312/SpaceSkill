package skillspace.skillspace_backend.Company.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class CompanyWriteServiceImpl implements CompanyWriteService {
    private final CompanyRepository companyRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public CompanyWriteServiceImpl(
        CompanyRepository companyRepository, 
        SecurityService securityService,
        UserRepository userRepository) {

        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    public StatusResponseDTO updateCompanyProfile(UpdateCompanyProfileDTO dto) {
        Company company = securityService.getCurrentCompany();
        if (dto.profileName() != null) company.setProfileName(dto.profileName());
        if (dto.location() != null) company.setLocation(dto.location());
        if (dto.about() != null) company.setAbout(dto.about());

        companyRepository.save(company);
        return new StatusResponseDTO(true, "Company profile updated successfully");
    }

    public StatusResponseDTO addRecruiter(UUID recruiterId) {
        Company company = securityService.getCurrentCompany();
        User user = userRepository.getUserByIdOrThrow(recruiterId);
        log.info("User with id {} was successfully found", user.getId());

        List<User> recruiters = company.getRecruiters();
        if (recruiters.contains(user)) {
            log.info("User {} is already a recruiter for company {}", user.getId(), company.getId());
            throw new IllegalArgumentException("User is already a recruiter for this company");
        }

        recruiters.add(user);
        company.setRecruiters(recruiters);
        companyRepository.save(company);
        return new StatusResponseDTO(true, "Recruiter added successfully");
    }

    public StatusResponseDTO removeRecruiter(UUID recruiterId) {
        Company company = securityService.getCurrentCompany();
        User user = userRepository.getUserByIdOrThrow(recruiterId);
        log.info("User with id {} was successfully found", user.getId());

        List<User> recruiters = company.getRecruiters();
        if (!recruiters.contains(user)) {
            log.info("User {} is not a recruiter for company {}", user.getId(), company.getId());
            throw new IllegalArgumentException("User is not a recruiter for this company");
        }

        recruiters.remove(user);
        company.setRecruiters(recruiters);
        companyRepository.save(company);
        return new StatusResponseDTO(true, "Recruiter removed successfully");
    }
}
