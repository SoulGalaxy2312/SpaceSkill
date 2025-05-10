package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.exception.UsernameExistsException;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public interface UserWriteService {
    User register(UserRegisterDTO dto) throws UsernameExistsException;
    
    // Experience section
    UserProfileDTO addExperience(UUID userId, AddExperienceDTO experience) throws UserNotFoundException;
    UserProfileDTO deleteExperience(UUID userId, UUID experienceId) throws UserNotFoundException;
    
    // Education section
    UserProfileDTO addEducation(UUID userId, AddEducationDTO educationDTO) throws UserNotFoundException;
    UserProfileDTO deleteEducation(UUID userId, UUID educationId) throws UserNotFoundException;
}
