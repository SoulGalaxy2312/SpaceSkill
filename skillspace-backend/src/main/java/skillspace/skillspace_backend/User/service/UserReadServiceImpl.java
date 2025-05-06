package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.mapper.UserMapper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

@Service
@Slf4j
public class UserReadServiceImpl implements UserReadService {
    private final UserRepository userRepository;

    public UserReadServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileDTO getUserProfile(UUID userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> {
                            log.warn("Get user profile failed: User with ID {} was not found.", userId);
                            return new UserNotFoundException(userId);
                        });

        return UserMapper.toUserProfileDTO(user);
    }
}
