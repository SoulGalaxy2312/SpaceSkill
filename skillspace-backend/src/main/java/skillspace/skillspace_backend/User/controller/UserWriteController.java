package skillspace.skillspace_backend.User.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.service.UserWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Slf4j
public class UserWriteController {
    private final UserWriteService userWriteService;

    public UserWriteController(UserWriteService userWriteService) {
        this.userWriteService = userWriteService;
    }

    @PostMapping(ApiPath.AUTH + "/register")
    public User register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("Attempting to register user with email: {}", userRegisterDTO.email());
        User newUser = userWriteService.register(userRegisterDTO);
        log.info("Successfully registered user with email: {}", userRegisterDTO.email());
        return newUser;
    }
    
    @PostMapping(ApiPath.USER + "/{userId}/experience")
    public UserProfileDTO addExperience(@PathVariable UUID userId, @RequestBody AddExperienceDTO experience) {
        log.info("Attempting to add experience to user with id: {}", userId);
        UserProfileDTO userProfileDTO = userWriteService.addExperience(userId, experience);
        log.info("Successfully add experience to user with id: {}", userId);
        return userProfileDTO;
    }
}
