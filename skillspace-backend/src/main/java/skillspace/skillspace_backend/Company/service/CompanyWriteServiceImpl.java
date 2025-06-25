package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class CompanyWriteServiceImpl implements CompanyWriteService {
    private final CompanyRepository companyRepository;
    private final SecurityService securityService;

    public CompanyWriteServiceImpl(
        CompanyRepository companyRepository, 
        SecurityService securityService) {

        this.companyRepository = companyRepository;
        this.securityService = securityService;
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
        UUID companyId = securityService.getCurrentCompany().getId();
        int isAddded = companyRepository.addRecruiter(companyId, recruiterId);
        if (isAddded == 0) {
            log.info("User is already a recruiter of company");
            throw new IllegalArgumentException("User is already a recruiter of this company");
        }

        return new StatusResponseDTO(true, "Recruiter added successfully");
    }

    public StatusResponseDTO removeRecruiter(UUID recruiterId) {
        UUID companyId = securityService.getCurrentCompany().getId();
        int isDeleted = companyRepository.removeRecruiter(companyId, recruiterId);
        if (isDeleted == 0) {
            log.info("User is not a recruiter of company");
            throw new IllegalArgumentException("User is not a recruiter of this company");
            
        }
        return new StatusResponseDTO(true, "Recruiter removed successfully");
    }
}
