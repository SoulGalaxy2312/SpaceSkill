package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public interface UserReadService {
    UserProfileDTO getUserProfile(UUID id) throws UserNotFoundException;
}
