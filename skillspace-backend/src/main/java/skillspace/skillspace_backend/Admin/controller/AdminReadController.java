package skillspace.skillspace_backend.Admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Admin.response.AdminBaseUserBrief;
import skillspace.skillspace_backend.Admin.service.AdminReadService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(ApiPath.ADMIN)
public class AdminReadController {
    
    private final AdminReadService adminReadService;

    public AdminReadController(AdminReadService adminReadService) {
        this.adminReadService = adminReadService;
    }
    
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminBaseUserBrief> getBaseUsers(
        @RequestParam(value = "sortBy", defaultValue = "email") String sortBy,
        @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        List<String> allowedSortBy = List.of("email", "profileName", "role");
        if (!allowedSortBy.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy parameter. Allowed values are: " + allowedSortBy);
        }

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return adminReadService.getBaseUsers(pageable);
    }
    

}
