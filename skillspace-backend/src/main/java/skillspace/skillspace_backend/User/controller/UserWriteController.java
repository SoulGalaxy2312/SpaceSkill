package skillspace.skillspace_backend.User.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.FollowRequestDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.service.UserWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Experience section
     */
    @PostMapping(ApiPath.USER + "/{userId}/experience")
    public UserProfileDTO addExperience(@PathVariable UUID userId, @RequestBody AddExperienceDTO experience) throws JsonProcessingException {
        log.info("Add experience to user with id: {}", userId);
        UserProfileDTO userProfileDTO = userWriteService.addExperience(userId, experience);
        log.info("Successfully add experience to user with id: {}", userId);
        return userProfileDTO;
    }

    @DeleteMapping(ApiPath.USER + "/{userId}/experience/{experienceId}") 
    public UserProfileDTO deleteExperience(@PathVariable UUID userId, @PathVariable UUID experienceId) throws JsonProcessingException {
        log.info("Delete experienceId {} of userId {}", experienceId, userId);
        UserProfileDTO userProfileDTO = userWriteService.deleteExperience(userId, experienceId);
        log.info("Successfully delete experienceId {} of userId {}", experienceId, userId);
        return userProfileDTO;
    }

    /**
     * Education section
     */
    @PostMapping(ApiPath.USER + "/{userId}/education")
    public UserProfileDTO addEducation(@PathVariable UUID userId, @RequestBody AddEducationDTO educationDTO) throws JsonProcessingException { 
        log.info("Add education to user with id: {}", userId);
        UserProfileDTO userProfileDTO = userWriteService.addEducation(userId, educationDTO);
        log.info("Successfully add education to user with id: {}", userId);
        return userProfileDTO;
    }
    
    @DeleteMapping(ApiPath.USER + "/{userId}/education/{educationId}") 
    public UserProfileDTO deleteEducation(@PathVariable UUID userId, @PathVariable UUID educationId) throws JsonProcessingException {
        log.info("Delete educationId {} of userId {}", educationId, userId);
        UserProfileDTO userProfileDTO = userWriteService.deleteEducation(userId, educationId);
        log.info("Successfully delete educationId {} of userId {}", educationId, userId);
        return userProfileDTO;
    }

    /**
     * Skill section
     */
    @PostMapping(ApiPath.USER + "/{userId}/skill/{skill}")
    public UserProfileDTO addSkill(@PathVariable UUID userId, @PathVariable String skill) throws JsonProcessingException {
        log.info("Add skill {} for userId {}", skill, userId);
        UserProfileDTO userProfileDTO = userWriteService.addSkill(userId, skill);
        log.info("Successfully add skill {}", skill);
        return userProfileDTO;
    }

    @DeleteMapping(ApiPath.USER + "/{userId}/skill/{skill}")
    public UserProfileDTO deleteSkill(@PathVariable UUID userId, @PathVariable String skill) throws JsonProcessingException {
        log.info("Delete skill {} for userId {}", skill, userId);
        UserProfileDTO userProfileDTO = userWriteService.deleteSkill(userId, skill);
        log.info("Successfully delete skill {}", skill);
        return userProfileDTO;
    }

    /**
     * Follow section
     */
    @PostMapping(ApiPath.USER + "follow")
    @PreAuthorize("hasRole('USER')")
    public void follow(@RequestBody FollowRequestDTO dto) {
        userWriteService.follow(dto);
    }
    
}
