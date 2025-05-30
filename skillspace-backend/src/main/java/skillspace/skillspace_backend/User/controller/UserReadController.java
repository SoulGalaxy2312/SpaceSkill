package skillspace.skillspace_backend.User.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Notification.response.NotificationResponseDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.service.UserReadService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.List;
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

    @GetMapping("/notifications")
    @PreAuthorize("hasRole('USER')")
    public List<NotificationResponseDTO> getNotifications(@RequestParam String param) {
        return null;
    }
    
    
}
