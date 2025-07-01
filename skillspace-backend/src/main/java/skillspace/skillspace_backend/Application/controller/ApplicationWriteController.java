package skillspace.skillspace_backend.Application.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Application.request.ProcessApplicationRequestDTO;
import skillspace.skillspace_backend.Application.service.ApplicationWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Slf4j
public class ApplicationWriteController {
    private final ApplicationWriteService applicationWriteService;

    public ApplicationWriteController(ApplicationWriteService applicationWriteService) {
        this.applicationWriteService = applicationWriteService;
    }
    
    @PostMapping(ApiPath.APPLICATION + "/{jobId}")
    @PreAuthorize("hasRole('USER')")
    public StatusResponseDTO applyJob(
        @PathVariable UUID jobId,
        @RequestBody(required = true) String personalStatement) {
            
        return applicationWriteService.applyJob(jobId, personalStatement);    
    }
    
    @PatchMapping(ApiPath.APPLICATION + "/{applicationId}")
    @PreAuthorize("isAuthenticated()")
    public StatusResponseDTO processApplication(@PathVariable UUID applicationId, @RequestBody ProcessApplicationRequestDTO dto) {
        log.debug("Attempting to process application with id {}", applicationId);
        return applicationWriteService.processApplication(applicationId, dto);
    }
    
}
