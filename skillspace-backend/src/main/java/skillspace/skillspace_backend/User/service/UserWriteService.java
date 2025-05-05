package skillspace.skillspace_backend.User.service;

import skillspace.skillspace_backend.User.exception.UsernameExistsException;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.request.UserRegisterDTO;

public interface UserWriteService {
    User register(UserRegisterDTO dto) throws UsernameExistsException;
}
