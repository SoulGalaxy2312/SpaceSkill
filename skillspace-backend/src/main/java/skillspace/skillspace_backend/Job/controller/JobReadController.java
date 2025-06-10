package skillspace.skillspace_backend.Job.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import skillspace.skillspace_backend.Job.request.JobSearchRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.Job.service.JobReadService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
public class JobReadController {
    private final JobReadService jobReadService;
    
    public JobReadController(JobReadService jobReadService) {
        this.jobReadService = jobReadService;
    }

    @GetMapping(ApiPath.JOB + "/search")
    public List<JobResponseDTO> searchJobs(
        @Valid @ModelAttribute JobSearchRequestDTO jobSearchRequestDTO
    ) {
        return jobReadService.searchJobs(
            jobSearchRequestDTO,
            jobSearchRequestDTO.getPage(),
            jobSearchRequestDTO.getSize()
        );
    }
    
}
