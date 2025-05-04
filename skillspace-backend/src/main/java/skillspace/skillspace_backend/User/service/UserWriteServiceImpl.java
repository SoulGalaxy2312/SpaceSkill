package skillspace.skillspace_backend.User.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.shared.exception.UsernameExistsException;

@Service
@Slf4j
public class UserWriteServiceImpl implements UserWriteService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserWriteServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegisterDTO userRegisterDTO) throws UsernameExistsException {
        boolean isEmailExists = userRepository
                                .findByEmail(userRegisterDTO.email())   
                                .isPresent();
        
        if (isEmailExists) {
            log.warn("Registration failed: Email {} already exists", userRegisterDTO.email());
            throw new UsernameExistsException();
        }

        User newUser = new User();
        newUser.setFirstName(userRegisterDTO.firstName());
        newUser.setLastName(userRegisterDTO.lastName());
        newUser.setLocation(userRegisterDTO.location());
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.password()));
        newUser.setEmail(userRegisterDTO.email());

        return userRepository.save(newUser);
    }
}
