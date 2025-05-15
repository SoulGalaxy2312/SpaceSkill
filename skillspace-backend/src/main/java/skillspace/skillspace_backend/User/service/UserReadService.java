package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public interface UserReadService {
    UserProfileDTO getUserProfile(UUID id) throws UserNotFoundException, JsonProcessingException;
}
