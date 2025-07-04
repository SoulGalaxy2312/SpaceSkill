package skillspace.skillspace_backend.Job.controller;

import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Job.request.JobRequestDTO;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;
import skillspace.skillspace_backend.Job.service.JobWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class JobWriteController {
    private final JobWriteService jobWriteService;

    public JobWriteController(JobWriteService jobWriteService) {
        this.jobWriteService = jobWriteService;
    }
    
    @PostMapping(ApiPath.JOB)
    @PreAuthorize("hasRole('COMPANY')")
    public JobResponseDTO createJob(@RequestBody JobRequestDTO dto) {
        return jobWriteService.createJob(dto);
    }
    
    @PatchMapping(ApiPath.JOB + "/{jobId}")
    @PreAuthorize("hasRole('COMPANY')")
    public JobResponseDTO updateJob(@PathVariable UUID jobId, @RequestBody JobRequestDTO dto) {
        return jobWriteService.updateJob(jobId, dto);
    }

    @DeleteMapping(ApiPath.JOB + "/{jobId}")
    @PreAuthorize("hasRole('COMPANY')") 
    public StatusResponseDTO deleteJob(@PathVariable UUID jobId) {
        return jobWriteService.deleteJob(jobId);
    }
}
