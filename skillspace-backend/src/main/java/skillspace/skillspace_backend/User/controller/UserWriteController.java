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
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

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
    @PostMapping(ApiPath.USER + "/experiences")
    @PreAuthorize("hasRole('USER')")
    public UserProfileDTO addExperience(@RequestBody AddExperienceDTO experience) throws JsonProcessingException {
        UserProfileDTO userProfileDTO = userWriteService.addExperience(experience);
        return userProfileDTO;
    }

    @DeleteMapping(ApiPath.USER + "/experiences/{experienceId}") 
    @PreAuthorize("hasRole('USER')")
    public UserProfileDTO deleteExperience(@PathVariable UUID experienceId) throws JsonProcessingException {
        UserProfileDTO userProfileDTO = userWriteService.deleteExperience(experienceId);
        return userProfileDTO;
    }

    /**
     * Education section
     */
    @PostMapping(ApiPath.USER + "/educations")
    @PreAuthorize("hasRole('USER')")
    public UserProfileDTO addEducation(@RequestBody AddEducationDTO educationDTO) throws JsonProcessingException { 
        UserProfileDTO userProfileDTO = userWriteService.addEducation(educationDTO);
        return userProfileDTO;
    }
    
    @DeleteMapping(ApiPath.USER + "/educations/{educationId}") 
    @PreAuthorize("hasRole('USER')")
    public UserProfileDTO deleteEducation(@PathVariable UUID educationId) throws JsonProcessingException {
        UserProfileDTO userProfileDTO = userWriteService.deleteEducation(educationId);
        return userProfileDTO;
    }

    /**
     * Skill section
     */
    @PostMapping(ApiPath.USER + "/skills/{skill}")
    @PreAuthorize("hasRole('USER')")
    public UserProfileDTO addSkill(@PathVariable String skill) throws JsonProcessingException {
        UserProfileDTO userProfileDTO = userWriteService.addSkill(skill);
        return userProfileDTO;
    }

    @DeleteMapping(ApiPath.USER + "/skills/{skill}")
    @PreAuthorize("hasRole('USER')")
    public UserProfileDTO deleteSkill(@PathVariable String skill) throws JsonProcessingException {
        UserProfileDTO userProfileDTO = userWriteService.deleteSkill(skill);
        return userProfileDTO;
    }

    /**
     * Follow section
     */
    @PostMapping(ApiPath.USER + "/follow")
    @PreAuthorize("hasRole('USER')")
    public StatusResponseDTO follow(@RequestBody FollowRequestDTO dto) {
        return userWriteService.follow(dto);
    }
    
}
