package skillspace.skillspace_backend.Job.service;

import java.util.UUID;

import skillspace.skillspace_backend.Job.request.JobSearchRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;
import skillspace.skillspace_backend.shared.response.PagingDTO;

public interface JobReadService {
    PagingDTO<JobResponseDTO> searchJobs(JobSearchRequestDTO jobSearchRequestDTO, int page, int size);
    PagingDTO<JobResponseDTO> getCompanyOpeningPositions(UUID companyId, int page, int size);
}