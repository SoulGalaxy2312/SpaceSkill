package skillspace.skillspace_backend.User.service;

import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.mapper.UserMapper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

@Service
@Slf4j
public class UserReadServiceImpl implements UserReadService {
    private final UserProfileDTOLoadAndCacheService cacheService;
    private final UserHelper userHelper;

    public UserReadServiceImpl(UserProfileDTOLoadAndCacheService cacheService, UserHelper userHelper) {
        this.cacheService = cacheService;
        this.userHelper = userHelper;
    }

    public UserProfileDTO getUserProfile(UUID userId) throws UserNotFoundException, JsonProcessingException {
        Supplier<UserProfileDTO> dataSupplier = () -> {
            try {
                User user = userHelper.getUserById(userId);
                return UserMapper.toUserProfileDTO(user);    
            } catch (UserNotFoundException ex) {
                throw ex;
            }
        };

        UserProfileDTO profile = cacheService.loadAndCacheUserProfileDTO(userId, dataSupplier);
        return profile;
    }
}
