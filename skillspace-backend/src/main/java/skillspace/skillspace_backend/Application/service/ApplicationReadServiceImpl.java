package skillspace.skillspace_backend.Application.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Application.service.retrievingApplication.RetrievingStrategy;
import skillspace.skillspace_backend.Application.service.retrievingApplication.RetrievingStrategyFactory;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.shared.response.PagingDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class ApplicationReadServiceImpl implements ApplicationReadService {
    
    private final SecurityService securityService;
    private final RetrievingStrategyFactory retrievingStrategyFactory;

    public ApplicationReadServiceImpl(SecurityService securityService, RetrievingStrategyFactory retrievingStrategyFactory) {
        this.securityService = securityService;
        this.retrievingStrategyFactory = retrievingStrategyFactory;
    }
    
    public PagingDTO<ApplicationResponseDTO> retrieveApplications(int page, int size) {
        BaseUser baseUser = securityService.getCurrentBaseUser();
        log.info("Atttempting to retrieve applications for user with id: {}", baseUser.getId());
        RetrievingStrategy retrievingStrategy = retrievingStrategyFactory.getTargetRetrievingStrategy(baseUser.getRole());
        log.info("Successfully retrieve applications");
        return retrievingStrategy.retrieveApplications(baseUser.getId(), page, size);
    }
    
}
