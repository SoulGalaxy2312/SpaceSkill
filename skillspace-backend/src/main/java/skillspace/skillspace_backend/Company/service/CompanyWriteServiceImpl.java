package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;

@Service
public class CompanyWriteServiceImpl implements CompanyWriteService {
    private final CompanyRepository companyRepository;
    private final CompanyHelper companyHelper;

    public CompanyWriteServiceImpl(CompanyRepository companyRepository, CompanyHelper companyHelper) {
        this.companyHelper = companyHelper;
        this.companyRepository = companyRepository;
    }

    public void updateCompanyProfile(UUID companyId, UpdateCompanyProfileDTO dto) {
        Company company = companyHelper.getCompany(companyId);

        if (dto.profileName() != null) company.setProfileName(dto.profileName());
        if (dto.location() != null) company.setLocation(dto.location());
        if (dto.about() != null) company.setAbout(dto.about());

        companyRepository.save(company);
    }
}
