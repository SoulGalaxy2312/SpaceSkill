package skillspace.skillspace_backend.auth.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.BaseUser.repository.BaseUserRepository;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.auth.exception.EmailAlreadyUsedException;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.RegisterDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;
import skillspace.skillspace_backend.shared.security.jwt.JwtTokenProvider;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final BaseUserRepository baseUserRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public AuthServiceImpl(
        PasswordEncoder passwordEncoder, 
        JwtTokenProvider jwtTokenProvider,
        AuthenticationManager authenticationManager,
        BaseUserRepository baseUserRepository,
        UserRepository userRepository,
        CompanyRepository companyRepository) {
        
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.baseUserRepository = baseUserRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
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

    public String login(LoginDTO dto) {
        authenticationManager
            .authenticate(
                new UsernamePasswordAuthenticationToken(
                    dto.email(),
                    dto.password()));
        
        return jwtTokenProvider.generateToken(dto.email());
    }
}
