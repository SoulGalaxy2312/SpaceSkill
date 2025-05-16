package skillspace.skillspace_backend.Job.service;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import skillspace.skillspace_backend.Job.request.JobRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;

public interface JobWriteService {
    JobResponseDTO createJob(JobRequestDTO createJobDTO);
    JobResponseDTO updateJob(UUID jobId, JobRequestDTO updateJobDTO) throws AccessDeniedException;
    void deleteJob(UUID jobId) throws AccessDeniedException;
}
