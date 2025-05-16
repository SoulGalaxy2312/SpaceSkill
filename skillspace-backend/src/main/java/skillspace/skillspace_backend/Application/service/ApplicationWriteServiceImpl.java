package skillspace.skillspace_backend.Application.service;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Application.mapper.ApplicationMapper;
import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.Application.repository.ApplicationRepository;
import skillspace.skillspace_backend.Application.request.ApplicationRequestDTO;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.service.JobHelper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.service.UserHelper;

@Service
public class ApplicationWriteServiceImpl implements ApplicationWriteService {
    private final JobHelper jobHelper;
    private final UserHelper userHelper;
    private final ApplicationRepository repository;

    public ApplicationWriteServiceImpl(JobHelper jobHelper, UserHelper userHelper, ApplicationRepository repository) {
        this.jobHelper = jobHelper;
        this.userHelper = userHelper;
        this.repository = repository;
    }

    public ApplicationResponseDTO applyJob(UUID jobId, ApplicationRequestDTO request) {
        Job job = jobHelper.getJob(jobId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userHelper.getUserByEmail(email);
        
        Application application = new Application();
        application.setJob(job);
        application.setResumeUrl(request.resumeUrl());
        application.setUser(user);
        
        Application saved = repository.save(application);
        return ApplicationMapper.toApplicationResponseDTO(saved);
    }    
}
