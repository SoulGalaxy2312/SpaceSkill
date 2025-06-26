package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.shared.response.PagingDTO;

public interface UserReadService {
    UserProfileDTO getUserProfile(UUID id) throws UserNotFoundException, JsonProcessingException;
    PagingDTO<BaseUserBrief> getFollowingCompanies(int page, int size);
    PagingDTO<BaseUserBrief> getConnections(int page, int size);
    PagingDTO<BaseUserBrief> searchUsers(String profileName, int page, int size);
}
