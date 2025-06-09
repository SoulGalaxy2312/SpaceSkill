package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.User.exception.DuplicateSkillException;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.FollowRequestDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface UserWriteService {
    // Experience section
    UserProfileDTO addExperience(UUID userId, AddExperienceDTO experience) throws JsonProcessingException, AccessDeniedException;    
    UserProfileDTO deleteExperience(UUID userId, UUID experienceId) throws JsonProcessingException, AccessDeniedException;
    
    // Education section
    UserProfileDTO addEducation(UUID userId, AddEducationDTO educationDTO) throws JsonProcessingException, AccessDeniedException;
    UserProfileDTO deleteEducation(UUID userId, UUID educationId) throws JsonProcessingException, AccessDeniedException;

    // Skill section
    UserProfileDTO addSkill(UUID userId, String skill) throws DuplicateSkillException, JsonProcessingException, AccessDeniedException;
    UserProfileDTO deleteSkill(UUID userId, String skill) throws JsonProcessingException, AccessDeniedException;

    // Follow section
    StatusResponseDTO follow(FollowRequestDTO followRequestDTO) throws IllegalArgumentException;
}
