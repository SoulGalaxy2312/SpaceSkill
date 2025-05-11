package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import skillspace.skillspace_backend.User.exception.DuplicateSkillException;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public interface UserWriteService {
    // Experience section
    UserProfileDTO addExperience(UUID userId, AddExperienceDTO experience);
    UserProfileDTO deleteExperience(UUID userId, UUID experienceId);
    
    // Education section
    UserProfileDTO addEducation(UUID userId, AddEducationDTO educationDTO);
    UserProfileDTO deleteEducation(UUID userId, UUID educationId);

    // Skill section
    UserProfileDTO addSkill(UUID userId, String skill) throws DuplicateSkillException;
    UserProfileDTO deleteSkill(UUID userId, String skill);
}
