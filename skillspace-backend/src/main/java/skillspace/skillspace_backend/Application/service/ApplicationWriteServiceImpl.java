package skillspace.skillspace_backend.Application.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Application.exception.ApplicationNotFoundException;
import skillspace.skillspace_backend.Application.mapper.ApplicationMapper;
import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.Application.repository.ApplicationRepository;
import skillspace.skillspace_backend.Application.request.ApplicationRequestDTO;
import skillspace.skillspace_backend.Application.request.ProcessApplicationRequestDTO;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.repository.JobRepository;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.enums.ApplicationStatus;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class ApplicationWriteServiceImpl implements ApplicationWriteService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final SecurityService securityService;

    public ApplicationWriteServiceImpl(ApplicationRepository applicationRepository, JobRepository jobRepository, SecurityService securityService) { 
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.securityService = securityService;
    }

    public ApplicationResponseDTO applyJob(UUID jobId, ApplicationRequestDTO request) {
        Job job = jobRepository.getJobByIdOrThrow(jobId);
        User user = securityService.getCurrentUser();
        
        Application application = new Application();
        application.setJob(job);
        application.setResumeUrl(request.resumeUrl());
        application.setUser(user);
        application.setStatus(ApplicationStatus.PENDING);
        
        Application saved = applicationRepository.save(application);
        return ApplicationMapper.toApplicationResponseDTO(saved);
    }    

    public ApplicationResponseDTO processApplication(UUID applicationId, ProcessApplicationRequestDTO dto) throws ApplicationNotFoundException {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> {
                log.warn("Application with id {} was not found", applicationId);
                return new ApplicationNotFoundException(applicationId);
            });
        
        log.debug("Application found");
        Company company = application.getJob().getCompany();
        List<User> authorizedUsers = company.getRecruiters();
        UUID currentActorId = securityService.getCurrentBaseUser().getId();
        log.debug("Current Actor found");

        Set<UUID> authorizedAccountsIds = Stream.concat(
            Stream.of(company.getId()),
            authorizedUsers.stream().map(User::getId)
            ).collect(Collectors.toSet());

        boolean isAuthorized = authorizedAccountsIds.contains(currentActorId);
        if (!isAuthorized) {
            log.warn("User with id {} is not authorized to do this action", currentActorId);
            throw new AccessDeniedException("User with id " + currentActorId + " is not authorized to do this action");
        }
        log.debug("User is authorized");

        application.setStatus(dto.status());
        application.setReviewerNote(dto.reviewerNote());
        Application saved = applicationRepository.save(application);
        return ApplicationMapper.toApplicationResponseDTO(saved);
    }
}
