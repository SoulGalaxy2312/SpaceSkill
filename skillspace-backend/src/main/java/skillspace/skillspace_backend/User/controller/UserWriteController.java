package skillspace.skillspace_backend.User.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.User.service.UserWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(ApiPath.AUTH)
public class UserWriteController {
    private final UserWriteService userWriteService;

    public UserWriteController(UserWriteService userWriteService) {
        this.userWriteService = userWriteService;
    }

    @PostMapping("/register")
    public skillspace.skillspace_backend.User.model.User register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userWriteService.register(userRegisterDTO);
    }
    
}
