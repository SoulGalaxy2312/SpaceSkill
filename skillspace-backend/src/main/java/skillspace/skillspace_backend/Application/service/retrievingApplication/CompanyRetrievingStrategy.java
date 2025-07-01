package skillspace.skillspace_backend.Application.service.retrievingApplication;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Application.mapper.ApplicationMapper;
import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.Application.repository.ApplicationRepository;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Application.specification.ApplicationSpecification;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.response.PagingDTO;

@Service
public class CompanyRetrievingStrategy implements RetrievingStrategy {

    private final ApplicationRepository applicationRepository;

    public CompanyRetrievingStrategy(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    
    public PagingDTO<ApplicationResponseDTO> retrieveApplications(UUID id, int page, int size) {
        Specification<Application> spec = Specification.where(ApplicationSpecification.ownedByCompany(id))
                                                        .and(ApplicationSpecification.sortByAppliedAtDesc());   
        Pageable pageable = PageRequest.of(page, size);

        Page<Application> applications = applicationRepository.findAll(spec, pageable);

        return new PagingDTO<>(
            applications.getContent()
                .stream()
                .map(ApplicationMapper::toApplicationResponseDTO)
                .collect(Collectors.toList()), 
            applications.getNumber(), 
            applications.getSize(), 
            applications.getTotalElements(), 
            applications.getTotalPages());
    }

    public UserRole getTargetUserRole() {
        return UserRole.COMPANY;
    }
}
