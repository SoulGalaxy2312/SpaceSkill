package skillspace.skillspace_backend.auth.service;

import skillspace.skillspace_backend.shared.security.UserPrincipal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.BaseUser.repository.BaseUserRepository;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.service.UserProfileDTOLoadAndCacheService;
import skillspace.skillspace_backend.auth.exception.EmailAlreadyUsedException;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.RegisterDTO;
import skillspace.skillspace_backend.auth.response.LoginSuccessDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;
import skillspace.skillspace_backend.shared.security.jwt.JwtTokenProvider;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final BaseUserRepository baseUserRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final SecurityService securityService;

    private final UserProfileDTOLoadAndCacheService userProfileDTOLoadAndCacheService;

    public AuthServiceImpl(
        PasswordEncoder passwordEncoder, 
        JwtTokenProvider jwtTokenProvider,
        AuthenticationManager authenticationManager,
        BaseUserRepository baseUserRepository,
        UserRepository userRepository,
        CompanyRepository companyRepository,
        SecurityService securityService,
        UserProfileDTOLoadAndCacheService userProfileDTOLoadAndCacheService) {
        
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.baseUserRepository = baseUserRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.userProfileDTOLoadAndCacheService = userProfileDTOLoadAndCacheService;
    }

    @Transactional
    public StatusResponseDTO register(RegisterDTO registerDTO) throws EmailAlreadyUsedException {
        Optional<BaseUser> baseUserOpt = baseUserRepository.findByEmail(registerDTO.email());
        if (baseUserOpt.isPresent()) {
            log.warn("Registration failed: Email {} already be used", registerDTO.email());
            throw new EmailAlreadyUsedException(registerDTO.email());
        }
        
        boolean isCompany = registerDTO.isCompany();
        String email = registerDTO.email();

        if (isCompany) {
            Company newCompany = new Company();
            newCompany.setProfileName(registerDTO.profileName());
            newCompany.setLocation(registerDTO.location());
            newCompany.setPassword(passwordEncoder.encode(registerDTO.password()));
            newCompany.setEmail(email);
            companyRepository.save(newCompany);
        } else {
            User newUser = new User();
            newUser.setProfileName(registerDTO.profileName().isBlank() ? "Anonymous" : registerDTO.profileName());
            newUser.setLocation(registerDTO.location());
            newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
            newUser.setEmail(registerDTO.email());
            userRepository.save(newUser);
        }
        return new StatusResponseDTO(true, "Registration successful");
    }

    public LoginSuccessDTO login(LoginDTO dto) {
        Authentication auth = authenticationManager
            .authenticate(
                new UsernamePasswordAuthenticationToken(
                    dto.email(),
                    dto.password()));
        
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        BaseUser user = userPrincipal.getUser();
        log.info("User with email {} logs in successfully", user.getEmail());
        return new LoginSuccessDTO(
            jwtTokenProvider.generateToken(user.getEmail()),
            user.getId(),
            user.getRole()
        );
    }

    public StatusResponseDTO logout() throws JsonProcessingException {
        UUID id = securityService.getCurrentBaseUserId();
        log.info("Attempting to logout for user with id: {}", id);
        userProfileDTOLoadAndCacheService.flushAll(id);
        log.info("Successfully logout for user with id: {}", id);
        return new StatusResponseDTO(true, "Successfully logout for user with id " + id);
    }
}
