package skillspace.skillspace_backend.User.service;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.shared.model.BaseUserBrief;

public interface UserReadService {
    UserProfileDTO getUserProfile(UUID id) throws UserNotFoundException, JsonProcessingException;
    List<BaseUserBrief> getFollowingCompanies(int page, int size);
    List<BaseUserBrief> getConnections(int page, int size);
    List<BaseUserBrief> searchUsers(String profileName, int page, int size);
}
