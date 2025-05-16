package skillspace.skillspace_backend.Job.service;

import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.service.CompanyHelper;
import skillspace.skillspace_backend.Job.mapper.JobMapper;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.repository.JobRepository;
import skillspace.skillspace_backend.Job.request.CreateJobRequestDTO;
import skillspace.skillspace_backend.Job.response.CreateJobResponseDTO;

@Service
public class JobWriteServiceImpl implements JobWriteService {
    private final JobRepository jobRepository;
    private final CompanyHelper companyHelper;

    public JobWriteServiceImpl(JobRepository jobRepository, CompanyHelper companyHelper) {
        this.jobRepository = jobRepository;
        this.companyHelper = companyHelper;
    }

    public CreateJobResponseDTO createJob(CreateJobRequestDTO createJobDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyHelper.getCompany(email);
        
        LocalDate createdAt = LocalDate.now();

        Job job = new Job();
        job.setTitle(createJobDTO.title());
        job.setCompany(company);
        job.setRequiredSkills(createJobDTO.requiredSkills());
        job.setDescription(createJobDTO.description());
        job.setCreatedAt(createdAt);

        CreateJobResponseDTO response = JobMapper.toCreateJobDTO(jobRepository.save(job));
        return response;
    }
}
