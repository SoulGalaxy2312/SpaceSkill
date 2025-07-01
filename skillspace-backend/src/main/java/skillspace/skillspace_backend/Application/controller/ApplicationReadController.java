package skillspace.skillspace_backend.Application.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Application.service.ApplicationReadService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.PagingDTO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
public class ApplicationReadController {

    private final ApplicationReadService applicationReadService;

    public ApplicationReadController(ApplicationReadService applicationReadService) {
        this.applicationReadService = applicationReadService;
    }
    
    @GetMapping(ApiPath.APPLICATION)
    @PreAuthorize("hasRole('USER') || hasRole('COMPANY')")
    public PagingDTO<ApplicationResponseDTO> getApplications(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "10") int size) {

        if (page < 0) {
            log.info("Page: {}", page);
            log.info("Page must not be smaller than 1");
            throw new IllegalArgumentException("Page must not be smaller than 1");
        }
        if (size < 1 || size > 100) {
            log.info("Size: {}", size);
            log.info("Size must be between 1 and 100");
            throw new IllegalArgumentException("Size must be between 1 and 100");
        } 

        return applicationReadService.retrieveApplications(page - 1, size);
    }
    
}
