package skillspace.skillspace_backend.User.service;

import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.mapper.UserMapper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class UserReadServiceImpl implements UserReadService {
    private final UserProfileDTOLoadAndCacheService cacheService;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public UserReadServiceImpl(UserProfileDTOLoadAndCacheService cacheService, SecurityService securityService, UserRepository userRepository) {
        this.cacheService = cacheService;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    public UserProfileDTO getUserProfile(UUID userId) throws UserNotFoundException, JsonProcessingException {
        Supplier<UserProfileDTO> dataSupplier = () -> {
            try {
                User user = userRepository.getUserByIdOrThrow(userId);    
                boolean isCurrentUser = securityService.assertCurrentUserMatches(userId);
                return UserMapper.toUserProfileDTO(user, isCurrentUser);
            } catch (UserNotFoundException ex) {
                throw ex;
            }
        };

        UserProfileDTO profile = cacheService.loadAndCacheUserProfileDTO(userId, dataSupplier);
        return profile;
    }
}
