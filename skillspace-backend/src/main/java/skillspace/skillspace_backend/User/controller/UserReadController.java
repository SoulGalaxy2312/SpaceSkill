package skillspace.skillspace_backend.User.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.service.UserReadService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.PagingDTO;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping(ApiPath.USER)
@Slf4j
public class UserReadController {
    
    private final UserReadService userReadService;

    public UserReadController(UserReadService userReadService) {
        this.userReadService = userReadService;
    } 

    @GetMapping("/{userId}/profile")
    public UserProfileDTO getUserProfile(@PathVariable UUID userId)  throws JsonProcessingException {
        log.info("Attempting to get user profile with id: {}", userId);
        UserProfileDTO profile = userReadService.getUserProfile(userId);
        log.info("Successfully get user profile with id: {}", userId);
        return profile;
    }    

    @GetMapping("/following-companies")
    @PreAuthorize("hasRole('USER')")
    public PagingDTO<BaseUserBrief> getFollowingCompanies(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
        log.info("Fetching following companies for page: {}, size: {}", page, size);
        return userReadService.getFollowingCompanies(page - 1, size);
    }
    
    @GetMapping("/connections")
    @PreAuthorize("hasRole('USER')")
    public PagingDTO<BaseUserBrief> getConnections(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
        log.info("Fetching connections for page: {}, size: {}", page, size);
        return userReadService.getConnections(page - 1, size);
    }
    
    @GetMapping("/search")
    public PagingDTO<BaseUserBrief> searchUsers(
        @RequestParam String profileName,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
        log.info("Searching for users with profile name: {}", profileName);
        return userReadService.searchUsers(profileName, page - 1, size);
    }
}
