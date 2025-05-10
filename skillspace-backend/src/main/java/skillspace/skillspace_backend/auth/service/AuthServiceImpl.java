package skillspace.skillspace_backend.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.exception.UsernameExistsException;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.auth.exception.WrongCredentialsException;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.UserRegisterDTO;
import skillspace.skillspace_backend.shared.security.jwt.JwtTokenProvider;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
        PasswordEncoder passwordEncoder, 
        UserRepository userRepository, 
        JwtTokenProvider jwtTokenProvider,
        AuthenticationManager authenticationManager) {
        
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) throws UsernameExistsException {
        boolean isEmailExists = userRepository
                                .findByEmail(userRegisterDTO.email())   
                                .isPresent();
        
        if (isEmailExists) {
            log.warn("Registration failed: Email {} already exists", userRegisterDTO.email());
            throw new UsernameExistsException();
        }

        User newUser = new User();
        newUser.setProfileName(userRegisterDTO.profileName().isBlank() ? "Anonymous" : userRegisterDTO.profileName());
        newUser.setLocation(userRegisterDTO.location());
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.password()));
        newUser.setEmail(userRegisterDTO.email());
        
        userRepository.save(newUser);
    }

    public String login(LoginDTO dto) throws WrongCredentialsException {
        Authentication authentication =
            authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        dto.email(), 
                        dto.password()));

        if (authentication == null || !authentication.isAuthenticated()) 
            throw new WrongCredentialsException("Wrong credentials");

        return jwtTokenProvider.generateToken(dto.email());
    }
}
