package skillspace.skillspace_backend.Job.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Job.exception.JobNotFoundException;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.repository.JobRepository;

@Service
@Slf4j
public class JobHelper {
    private final JobRepository jobRepository;

    public JobHelper(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job getJob(UUID id) {
        return jobRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Job with id {} was not found", id);
                        return new JobNotFoundException(id);
                    });
    }
}
