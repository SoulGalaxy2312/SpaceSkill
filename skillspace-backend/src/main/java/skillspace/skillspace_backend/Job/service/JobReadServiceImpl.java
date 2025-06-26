package skillspace.skillspace_backend.Job.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Job.mapper.JobMapper;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.repository.JobRepository;
import skillspace.skillspace_backend.Job.request.JobSearchRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;
import skillspace.skillspace_backend.Job.specification.JobSpecification;
import skillspace.skillspace_backend.shared.response.PagingDTO;

@Service
public class JobReadServiceImpl implements JobReadService {

    private final JobRepository jobRepository;
    public JobReadServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public PagingDTO<JobResponseDTO> searchJobs(JobSearchRequestDTO jobSearchRequestDTO, int page, int size) {
        String title = safeTrim(jobSearchRequestDTO.getTitle());
        String location = safeTrim(jobSearchRequestDTO.getLocation());
        String skill = safeTrim(jobSearchRequestDTO.getSkill());
        String company = safeTrim(jobSearchRequestDTO.getCompany());

        Specification<Job> spec = Specification.where(null);
        if (title != null) {
            spec = spec.and(JobSpecification.fullTextTitleSearch(title));
        }
        if (location != null) {
            spec = spec.and(JobSpecification.isLocatedAt(location));
        }
        if (skill != null) {
            spec = spec.and(JobSpecification.hasRequiredSkill(skill));
        }
        if (company != null) {
            spec = spec.and(JobSpecification.fromCompany(company));
        }
        spec = spec.and(JobSpecification.sortByCreatedAtDesc());
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Job> jobs = jobRepository.findAll(spec, pageable);
        return new PagingDTO<>(
            jobs.getContent().stream()
                .map(JobMapper::toJobResponseDTO)
                .collect(Collectors.toList()),
            jobs.getNumber(),
            jobs.getSize(),
            jobs.getTotalElements(),
            jobs.getTotalPages()
        );
    }
    
    private String safeTrim(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        return input.trim();
    }
}
