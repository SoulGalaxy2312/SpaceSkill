package skillspace.skillspace_backend.Job.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.service.CompanyHelper;
import skillspace.skillspace_backend.Job.mapper.JobMapper;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.repository.JobRepository;
import skillspace.skillspace_backend.Job.request.JobRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;
import skillspace.skillspace_backend.Notification.service.NotificationWriteService;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

@Service
@Slf4j
public class JobWriteServiceImpl implements JobWriteService {

    private final JobRepository jobRepository;
    private final CompanyHelper companyHelper;
    private final JobHelper jobHelper;
    private final NotificationWriteService notificationWriteService;

    public JobWriteServiceImpl(JobRepository jobRepository, CompanyHelper companyHelper, JobHelper jobHelper, NotificationWriteService notificationWriteService) {
        this.jobRepository = jobRepository;
        this.companyHelper = companyHelper;
        this.jobHelper = jobHelper;
        this.notificationWriteService = notificationWriteService;
    }

    public JobResponseDTO createJob(JobRequestDTO createJobDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyHelper.getCompany(email);
        List<User> followers = new ArrayList<>(company.getFollowers());

        LocalDate createdAt = LocalDate.now();
        Job job = new Job();
        job.setTitle(createJobDTO.title());
        job.setCompany(company);
        job.setRequiredSkills(createJobDTO.requiredSkills());
        job.setDescription(createJobDTO.description());
        job.setCreatedAt(createdAt);
        job.setLocation(company.getLocation());

        JobResponseDTO response = JobMapper.toJobResponseDTO(jobRepository.save(job));
        notificationWriteService.sendNotification(company, followers, job);
        return response;
    }

    public JobResponseDTO updateJob(UUID jobId, JobRequestDTO jobRequestDTO) throws AccessDeniedException {  
        Job job = jobHelper.getJob(jobId);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyHelper.getCompany(email);

        if (!job.getCompany().getId().equals(company.getId())) {
            log.warn("Current company is not authorized to update this job");
            throw new AccessDeniedException("You are not authorized to update this job");
        }
    
        if (jobRequestDTO.description() != null) job.setDescription(jobRequestDTO.description());
        if (jobRequestDTO.requiredSkills() != null && 
                !jobRequestDTO.requiredSkills().isEmpty()) job.setRequiredSkills(jobRequestDTO.requiredSkills());
        if (jobRequestDTO.title() != null) job.setTitle(jobRequestDTO.title());

        Job savedJob = jobRepository.save(job);
        return JobMapper.toJobResponseDTO(savedJob);
    }

    public StatusResponseDTO deleteJob(UUID jobId) throws AccessDeniedException {
        Job job = jobHelper.getJob(jobId);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyHelper.getCompany(email);

        if (!job.getCompany().getId().equals(company.getId())) {
            log.warn("Current company is not authorized to update this job");
            throw new AccessDeniedException("You are not authorized to update this job");
        }

        jobRepository.delete(job);
        return new StatusResponseDTO(true, "Job deleted successfully");
    }
}
