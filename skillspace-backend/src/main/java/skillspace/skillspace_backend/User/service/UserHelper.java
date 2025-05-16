package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;

@Component
@Slf4j
public class UserHelper {
    private final UserRepository userRepository;

    public UserHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(UUID userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                    .orElseThrow(() -> {
                        log.warn("User with id {} was not found", userId);
                        return new UserNotFoundException(userId);
                    });
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        log.warn("User with email {} was not found", email);
                        return new UserNotFoundException(email);
                    });
    }
}
