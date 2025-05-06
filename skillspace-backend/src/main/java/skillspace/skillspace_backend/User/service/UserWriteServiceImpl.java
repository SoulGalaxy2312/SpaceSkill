package skillspace.skillspace_backend.User.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.exception.UsernameExistsException;
import skillspace.skillspace_backend.User.mapper.UserMapper;
import skillspace.skillspace_backend.User.model.Experience;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

@Service
@Slf4j
public class UserWriteServiceImpl implements UserWriteService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserWriteServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(UserRegisterDTO userRegisterDTO) throws UsernameExistsException {
        boolean isEmailExists = userRepository
                                .findByEmail(userRegisterDTO.email())   
                                .isPresent();
        
        if (isEmailExists) {
            log.warn("Registration failed: Email {} already exists", userRegisterDTO.email());
            throw new UsernameExistsException();
        }

        User newUser = new User();
        newUser.setProfileName(userRegisterDTO.profileName());
        newUser.setLocation(userRegisterDTO.location());
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.password()));
        newUser.setEmail(userRegisterDTO.email());

        return userRepository.save(newUser);
    }

    @Transactional
    public UserProfileDTO addExperience(UUID userId, AddExperienceDTO dto) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> {
                            log.warn("Add experience fail: user with id {} was not found", userId);
                            return new UserNotFoundException(userId);
                        });
        
        Experience entity = new Experience();
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        entity.setCompany(dto.company());
        entity.setTitle(dto.title());
        entity.setUser(user);

        user.getExperiences().add(entity);
        return UserMapper.toUserProfileDTO(userRepository.save(user));
    }
}
