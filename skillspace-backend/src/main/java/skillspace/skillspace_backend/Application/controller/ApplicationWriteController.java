package skillspace.skillspace_backend.Application.controller;

import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Application.request.ApplicationRequestDTO;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Application.service.ApplicationWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ApplicationWriteController {
    private final ApplicationWriteService applicationWriteService;

    public ApplicationWriteController(ApplicationWriteService applicationWriteService) {
        this.applicationWriteService = applicationWriteService;
    }
    
    @PostMapping(ApiPath.APPLICATION + "/{jobId}/applyJob")
    @PreAuthorize("hasRole('USER')")
    public ApplicationResponseDTO applyJob(@PathVariable UUID jobId, @RequestBody ApplicationRequestDTO requestDTO) {
        return applicationWriteService.applyJob(jobId, requestDTO);    
    }
    
}
