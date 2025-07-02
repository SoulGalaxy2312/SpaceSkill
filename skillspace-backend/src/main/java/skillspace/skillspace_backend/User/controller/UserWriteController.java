package skillspace.skillspace_backend.User.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.FollowRequestDTO;
import skillspace.skillspace_backend.User.response.ExperienceDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.service.UserWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ExperienceDTO addExperience(@RequestBody AddExperienceDTO experience) throws JsonProcessingException {
        return userWriteService.addExperience(experience);
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
    @PostMapping(ApiPath.USER + "/skills")
    @PreAuthorize("hasRole('USER')")
    public String addSkill(@RequestBody String skill) throws JsonProcessingException {
        return userWriteService.addSkill(skill);
    }

    @DeleteMapping(ApiPath.USER + "/skills/{skill}")
    @PreAuthorize("hasRole('USER')")
    public StatusResponseDTO deleteSkill(@PathVariable String skill) throws JsonProcessingException {
        return userWriteService.deleteSkill(skill);
    }

    /**
     * Follow section
     */
    @PutMapping(ApiPath.USER + "/follow")
    @PreAuthorize("hasRole('USER')")
    public StatusResponseDTO follow(@RequestBody FollowRequestDTO dto) {
        log.info("Attempting to follow BaseUser with id: {}", dto.targetId());
        return userWriteService.follow(dto);
    }

    @PutMapping(ApiPath.USER + "/unfollow")
    @PreAuthorize("hasRole('USER')")
    public StatusResponseDTO unfollow(@RequestBody FollowRequestDTO dto) {
        log.info("Attempting to unfollow BaseUser with id: {}", dto.targetId());
        return userWriteService.unfollow(dto);
    }
}
