package skillspace.skillspace_backend.Company.controller;

import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.Company.service.CompanyReadService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.PagingDTO;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CompanyReadController {
    private final CompanyReadService companyReadService;

    public CompanyReadController(CompanyReadService companyReadService) {
        this.companyReadService = companyReadService;
    }

    @GetMapping(ApiPath.COMPANY + "/{companyId}/profile")
    public CompanyProfileDTO getCompanyProfile(@PathVariable UUID companyId) {
        return companyReadService.getCompanyProfile(companyId);
    }

    @GetMapping(ApiPath.COMPANY + "/recruiters")
    @PreAuthorize("hasRole('COMPANY')")
    public PagingDTO<BaseUserBrief> getRecruiters(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size) {
        
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }

        return companyReadService.getRecruiters(page - 1, size);
    }
    
}
