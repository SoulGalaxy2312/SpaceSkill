package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.mapper.CompanyMapper;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;

@Service
public class CompanyReadServiceImpl implements CompanyReadService {
    private final CompanyHelper companyHelper;
    private final CompanyRepository companyRepository;

    public CompanyReadServiceImpl(CompanyRepository companyRepository, CompanyHelper companyHelper) {
        this.companyRepository = companyRepository;
        this.companyHelper = companyHelper;
    }

    public CompanyProfileDTO getCompanyProfile(UUID companyId) {
        Company company = companyHelper.getCompany(companyId);
        return CompanyMapper.toCompanyProfileDTO(company);
    }
}
