package skillspace.skillspace_backend.Company.controller;

import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.Company.service.CompanyReadService;
import skillspace.skillspace_backend.User.response.UserBriefDTO;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping(ApiPath.COMPANY + "/{companyId}/getRecruiters")
    public List<UserBriefDTO> getRecruiters(@PathVariable UUID companyId) {
        return companyReadService.getRecruiters(companyId);
    }
    
}
