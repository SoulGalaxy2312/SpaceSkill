package skillspace.skillspace_backend.Company.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Company.service.CompanyWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class CompanyWriteController {
    private final CompanyWriteService companyWriteService;

    public CompanyWriteController(CompanyWriteService companyWriteService) {
        this.companyWriteService = companyWriteService;
    }

    @PostMapping(ApiPath.COMPANY + "/recruiters/{recruiterId}")
    @PreAuthorize("hasRole('COMPANY')")
    public StatusResponseDTO addRecruiter(@PathVariable UUID recruiterId) {
        return companyWriteService.addRecruiter(recruiterId);
    }
    
    @DeleteMapping(ApiPath.COMPANY + "/recruiters/{recruiterId}")
    @PreAuthorize("hasRole('COMPANY')")
    public StatusResponseDTO removeRecruiter(@PathVariable UUID recruiterId) {
        return companyWriteService.removeRecruiter(recruiterId);
    }
}
