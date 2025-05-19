package skillspace.skillspace_backend.Company.controller;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Company.request.AddRecruiterDTO;
import skillspace.skillspace_backend.Company.request.UpdateCompanyProfileDTO;
import skillspace.skillspace_backend.Company.service.CompanyWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class CompanyWriteController {
    private final CompanyWriteService companyWriteService;

    public CompanyWriteController(CompanyWriteService companyWriteService) {
        this.companyWriteService = companyWriteService;
    }

    @PatchMapping(ApiPath.COMPANY + "/{companyId}/profile")
    @PreAuthorize("hasRole('COMPANY')")
    public void updateProfile(@PathVariable UUID companyId, @RequestBody UpdateCompanyProfileDTO dto) {
        companyWriteService.updateCompanyProfile(companyId, dto);
    }

    @PostMapping(ApiPath.COMPANY + "/{companyId}/recruiter")
    @PreAuthorize("hasRole('COMPANY')")
    public void addRecruiter(@PathVariable UUID companyId, @RequestBody AddRecruiterDTO dto) throws AccessDeniedException {
        companyWriteService.addRecruiter(companyId, dto);
    }
    
}
