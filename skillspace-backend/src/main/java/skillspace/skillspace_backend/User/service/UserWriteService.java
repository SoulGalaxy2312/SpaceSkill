package skillspace.skillspace_backend.User.service;

import skillspace.skillspace_backend.User.request.UserRegisterDTO;
import skillspace.skillspace_backend.shared.exception.UsernameExistsException;

public interface UserWriteService {
    skillspace.skillspace_backend.User.model.User register(UserRegisterDTO dto) throws UsernameExistsException;
}
