package skillspace.skillspace_backend.Company.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skillspace.skillspace_backend.Company.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>{   
    Optional<Company> findByEmail(String email);
}