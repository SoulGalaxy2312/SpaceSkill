package skillspace.skillspace_backend.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.RegisterDTO;
import skillspace.skillspace_backend.auth.response.JwtAuthenticationResponse;
import skillspace.skillspace_backend.auth.service.AuthService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

@RestController
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping(ApiPath.AUTH + "/register")
    public StatusResponseDTO register(@RequestBody RegisterDTO userRegisterDTO) {
        log.info("Register user with email: {}", userRegisterDTO.email());
        return authService.register(userRegisterDTO);
    }

    @PostMapping(ApiPath.AUTH + "/login")
    public JwtAuthenticationResponse login(@RequestBody LoginDTO logInDTO) {
        log.info("Log in user with email: {}", logInDTO.email());
        String token = authService.login(logInDTO);
        log.info("Successfully login for user with email: {}", logInDTO.email());
        return new JwtAuthenticationResponse(token);
    }
    
}
