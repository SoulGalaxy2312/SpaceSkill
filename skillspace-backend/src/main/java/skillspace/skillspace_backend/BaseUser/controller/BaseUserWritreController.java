package skillspace.skillspace_backend.BaseUser.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.BaseUser.request.EditProfileRequestDTO;
import skillspace.skillspace_backend.BaseUser.service.BaseUserWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

@RestController
@RequestMapping(ApiPath.BASE_USER)
public class BaseUserWritreController {
    
    private final BaseUserWriteService baseUserWriteService;

    public BaseUserWritreController(BaseUserWriteService baseUserWriteService) {
        this.baseUserWriteService = baseUserWriteService;
    }

    @PatchMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public StatusResponseDTO editProfile(@RequestBody EditProfileRequestDTO dto) {
        return baseUserWriteService.editProfile(dto);
    }

}
