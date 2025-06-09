package skillspace.skillspace_backend.Job.service;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import skillspace.skillspace_backend.Job.request.JobRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface JobWriteService {
    JobResponseDTO createJob(JobRequestDTO createJobDTO);
    JobResponseDTO updateJob(UUID jobId, JobRequestDTO updateJobDTO) throws AccessDeniedException;
    StatusResponseDTO deleteJob(UUID jobId) throws AccessDeniedException;
}
