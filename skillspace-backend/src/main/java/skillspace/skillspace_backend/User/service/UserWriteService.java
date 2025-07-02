package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.User.exception.DuplicateSkillException;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.FollowRequestDTO;
import skillspace.skillspace_backend.User.response.ExperienceDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface UserWriteService {
    // Experience section
    ExperienceDTO addExperience(AddExperienceDTO experience) throws JsonProcessingException;    
    UserProfileDTO deleteExperience(UUID experienceId) throws JsonProcessingException;
    
    // Education section
    UserProfileDTO addEducation(AddEducationDTO educationDTO) throws JsonProcessingException;
    UserProfileDTO deleteEducation(UUID educationId) throws JsonProcessingException;

    // Skill section
    String addSkill(String skill) throws DuplicateSkillException, JsonProcessingException;
    StatusResponseDTO deleteSkill(String skill) throws JsonProcessingException;

    // Follow section
    StatusResponseDTO follow(FollowRequestDTO followRequestDTO);
    StatusResponseDTO unfollow(FollowRequestDTO followRequestDTO);
}
