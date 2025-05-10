package skillspace.skillspace_backend.auth.service;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.exception.UsernameExistsException;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.UserRegisterDTO;

public interface AuthService {
    void register(UserRegisterDTO dto) throws UsernameExistsException;
    String login(LoginDTO dto) throws UserNotFoundException;
}