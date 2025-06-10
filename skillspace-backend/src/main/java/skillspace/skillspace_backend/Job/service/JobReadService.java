package skillspace.skillspace_backend.Job.service;

import java.util.List;

import skillspace.skillspace_backend.Job.request.JobSearchRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;

public interface JobReadService {
    List<JobResponseDTO> searchJobs(JobSearchRequestDTO jobSearchRequestDTO, int page, int size);
}