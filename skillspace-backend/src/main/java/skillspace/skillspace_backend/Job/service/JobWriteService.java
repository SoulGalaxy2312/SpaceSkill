package skillspace.skillspace_backend.Job.service;

import skillspace.skillspace_backend.Job.request.CreateJobRequestDTO;
import skillspace.skillspace_backend.Job.response.CreateJobResponseDTO;

public interface JobWriteService {
    CreateJobResponseDTO createJob(CreateJobRequestDTO createJobDTO);
}
