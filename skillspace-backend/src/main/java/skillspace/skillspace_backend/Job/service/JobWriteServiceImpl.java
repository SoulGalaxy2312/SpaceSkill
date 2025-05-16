package skillspace.skillspace_backend.Job.service;

import java.time.LocalDate;
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

@Service
@Slf4j
public class JobWriteServiceImpl implements JobWriteService {

    private final JobRepository jobRepository;
    private final CompanyHelper companyHelper;
    private final JobHelper jobHelper;

    public JobWriteServiceImpl(JobRepository jobRepository, CompanyHelper companyHelper, JobHelper jobHelper) {
        this.jobRepository = jobRepository;
        this.companyHelper = companyHelper;
        this.jobHelper = jobHelper;
    }

    public JobResponseDTO createJob(JobRequestDTO createJobDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyHelper.getCompany(email);
        
        LocalDate createdAt = LocalDate.now();

        Job job = new Job();
        job.setTitle(createJobDTO.title());
        job.setCompany(company);
        job.setRequiredSkills(createJobDTO.requiredSkills());
        job.setDescription(createJobDTO.description());
        job.setCreatedAt(createdAt);

        JobResponseDTO response = JobMapper.toJobResponseDTO(jobRepository.save(job));
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
}
