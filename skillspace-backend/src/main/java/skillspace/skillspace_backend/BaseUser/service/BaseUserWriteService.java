package skillspace.skillspace_backend.BaseUser.service;

import skillspace.skillspace_backend.BaseUser.request.EditProfileRequestDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface BaseUserWriteService {
    StatusResponseDTO editProfile(EditProfileRequestDTO dto);
}
