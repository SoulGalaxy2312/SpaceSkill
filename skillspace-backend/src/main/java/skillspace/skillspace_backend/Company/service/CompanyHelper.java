package skillspace.skillspace_backend.Company.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.exception.CompanyNotFoundException;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;

@Component
@Slf4j
public class CompanyHelper {
    private final CompanyRepository companyRepository;

    public CompanyHelper(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company getCompany(UUID companyId) throws CompanyNotFoundException {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> {
                    log.warn("Company with id {} was not found", companyId);
                    return new CompanyNotFoundException(companyId);
                });
    }

    public Company getCompany(String email) throws CompanyNotFoundException {
        return companyRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Company with email {} was not found", email);
                    return new CompanyNotFoundException(email);
                });
    }
}
