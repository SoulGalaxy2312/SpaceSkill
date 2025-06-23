package skillspace.skillspace_backend.Company.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skillspace.skillspace_backend.Company.exception.CompanyNotFoundException;
import skillspace.skillspace_backend.Company.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>{   
    default Company getCompanyByIdOrThrow(UUID companyId) {
        return findById(companyId).orElseThrow(() -> {
            throw new CompanyNotFoundException(companyId);
        });
    }

    default Company getCompanyByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(() -> {
            throw new CompanyNotFoundException(email);
        });
    }
    
    Optional<Company> findByEmail(String email);
}