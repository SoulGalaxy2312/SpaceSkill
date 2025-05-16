package skillspace.skillspace_backend.Job.controller;

import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Job.request.CreateJobRequestDTO;
import skillspace.skillspace_backend.Job.response.CreateJobResponseDTO;
import skillspace.skillspace_backend.Job.service.JobWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class JobWriteController {
    private final JobWriteService jobWriteService;

    public JobWriteController(JobWriteService jobWriteService) {
        this.jobWriteService = jobWriteService;
    }
    
    @PostMapping(ApiPath.JOB + "/createJob")
    @PreAuthorize("hasRole('COMPANY')")
    public CreateJobResponseDTO createJob(@RequestBody CreateJobRequestDTO dto) {
        return jobWriteService.createJob(dto);
    }
    
}
