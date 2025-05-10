package skillspace.skillspace_backend.User.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.service.UserWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Slf4j
public class UserWriteController {
    private final UserWriteService userWriteService;

    public UserWriteController(UserWriteService userWriteService) {
        this.userWriteService = userWriteService;
    }

    @PostMapping(ApiPath.AUTH + "/register")
    public User register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("Register user with email: {}", userRegisterDTO.email());
        User newUser = userWriteService.register(userRegisterDTO);
        log.info("Successfully registered user with email: {}", userRegisterDTO.email());
        return newUser;
    }
    
    /**
     * Experience section
     */
    @PostMapping(ApiPath.USER + "/{userId}/experience")
    public UserProfileDTO addExperience(@PathVariable UUID userId, @RequestBody AddExperienceDTO experience) {
        log.info("Add experience to user with id: {}", userId);
        UserProfileDTO userProfileDTO = userWriteService.addExperience(userId, experience);
        log.info("Successfully add experience to user with id: {}", userId);
        return userProfileDTO;
    }

    @DeleteMapping(ApiPath.USER + "/{userId}/experience/{experienceId}") 
    public UserProfileDTO deleteExperience(@PathVariable UUID userId, @PathVariable UUID experienceId) {
        log.info("Delete experienceId {} of userId {}", experienceId, userId);
        UserProfileDTO userProfileDTO = userWriteService.deleteExperience(userId, experienceId);
        log.info("Successfully delete experienceId {} of userId {}", experienceId, userId);
        return userProfileDTO;
    }

    /**
     * Education section
     */
    @PostMapping(ApiPath.USER + "/{userId}/education")
    public UserProfileDTO addEducation(@PathVariable UUID userId, @RequestBody AddEducationDTO educationDTO) { 
        log.info("Add education to user with id: {}", userId);
        UserProfileDTO userProfileDTO = userWriteService.addEducation(userId, educationDTO);
        log.info("Successfully add education to user with id: {}", userId);
        return userProfileDTO;
    }
    
    @DeleteMapping(ApiPath.USER + "/{userId}/education/{educationId}") 
    public UserProfileDTO deleteEducation(@PathVariable UUID userId, @PathVariable UUID educationId) {
        log.info("Delete educationId {} of userId {}", educationId, userId);
        UserProfileDTO userProfileDTO = userWriteService.deleteEducation(userId, educationId);
        log.info("Successfully delete educationId {} of userId {}", educationId, userId);
        return userProfileDTO;
    }
}
