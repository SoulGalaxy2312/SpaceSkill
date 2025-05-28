package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.User.exception.DuplicateSkillException;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.FollowRequestDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public interface UserWriteService {
    // Experience section
    UserProfileDTO addExperience(UUID userId, AddExperienceDTO experience) throws JsonProcessingException;
    UserProfileDTO deleteExperience(UUID userId, UUID experienceId) throws JsonProcessingException;
    
    // Education section
    UserProfileDTO addEducation(UUID userId, AddEducationDTO educationDTO) throws JsonProcessingException;
    UserProfileDTO deleteEducation(UUID userId, UUID educationId) throws JsonProcessingException;

    // Skill section
    UserProfileDTO addSkill(UUID userId, String skill) throws DuplicateSkillException, JsonProcessingException;
    UserProfileDTO deleteSkill(UUID userId, String skill) throws JsonProcessingException;

    // Follow section
    void follow(FollowRequestDTO followRequestDTO) throws IllegalArgumentException;
}
