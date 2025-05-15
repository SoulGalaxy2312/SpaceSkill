package skillspace.skillspace_backend.auth.service;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.auth.exception.EmailAlreadyUsedException;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.RegisterDTO;

public interface AuthService {
    void register(RegisterDTO dto) throws EmailAlreadyUsedException;
    String login(LoginDTO dto) throws UserNotFoundException;
}