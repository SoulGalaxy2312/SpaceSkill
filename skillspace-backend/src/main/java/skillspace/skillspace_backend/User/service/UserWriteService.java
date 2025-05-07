package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.exception.UsernameExistsException;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public interface UserWriteService {
    User register(UserRegisterDTO dto) throws UsernameExistsException;
    UserProfileDTO addExperience(UUID userId, AddExperienceDTO experience) throws UserNotFoundException;
    UserProfileDTO deleteExperience(UUID userId, UUID experienceId) throws UserNotFoundException;
}
