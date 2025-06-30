package skillspace.skillspace_backend.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.auth.exception.EmailAlreadyUsedException;
import skillspace.skillspace_backend.auth.request.LoginDTO;
import skillspace.skillspace_backend.auth.request.RegisterDTO;
import skillspace.skillspace_backend.auth.response.LoginSuccessDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface AuthService {
    StatusResponseDTO register(RegisterDTO dto) throws EmailAlreadyUsedException;
    LoginSuccessDTO login(LoginDTO dto) throws UserNotFoundException;
    StatusResponseDTO logout() throws JsonProcessingException;
}