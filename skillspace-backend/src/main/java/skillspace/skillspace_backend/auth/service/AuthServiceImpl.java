package skillspace.skillspace_backend.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.auth.exception.EmailAlreadyUsedException;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.RegisterDTO;
import skillspace.skillspace_backend.shared.security.jwt.JwtTokenProvider;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CompanyRepository companyRepository;

    public AuthServiceImpl(
        PasswordEncoder passwordEncoder, 
        UserRepository userRepository, 
        JwtTokenProvider jwtTokenProvider,
        AuthenticationManager authenticationManager,
        CompanyRepository companyRepository) {
        
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void register(RegisterDTO registerDTO) throws EmailAlreadyUsedException {
        boolean isCompany = registerDTO.isCompany();
        String email = registerDTO.email();

        if (companyRepository.findByEmail(email).isPresent()
            || userRepository.findByEmail(email).isPresent()) {
                log.warn("Registration failed: Email {} already be used", email);
                throw new EmailAlreadyUsedException(email);
            }
        
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
