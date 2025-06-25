package skillspace.skillspace_backend.Company.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
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

    @Modifying
    @Transactional
    @Query(
        value = """
                INSERT INTO company_recruiters (company_id, user_id) 
                VALUES (:companyId, :recruiterId)
                ON CONFLICT DO NOTHING
                """,
        nativeQuery = true
    )
    int addRecruiter(UUID companyId, UUID recruiterId);

    @Modifying
    @Transactional
    @Query(
        value = """
                DELETE FROM company_recruiters 
                WHERE company_id = :companyId AND user_id = :recruiterId
                """,
        nativeQuery = true
    )
    int removeRecruiter(UUID companyId, UUID recruiterId);
}