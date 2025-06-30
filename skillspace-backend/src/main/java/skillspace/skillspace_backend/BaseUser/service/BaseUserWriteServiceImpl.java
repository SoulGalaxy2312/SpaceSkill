package skillspace.skillspace_backend.BaseUser.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.BaseUser.repository.BaseUserRepository;
import skillspace.skillspace_backend.BaseUser.request.EditProfileRequestDTO;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class BaseUserWriteServiceImpl implements BaseUserWriteService {
    private final BaseUserRepository baseUserRepository;
    private final SecurityService securityService;

    public BaseUserWriteServiceImpl(
        BaseUserRepository baseUserRepository,
        SecurityService securityService) {

        this.baseUserRepository = baseUserRepository;
        this.securityService = securityService;
    }

    @Transactional
    public StatusResponseDTO editProfile(EditProfileRequestDTO dto) {
        BaseUser baseUser = securityService.getCurrentBaseUser();

        log.info("Attempting to edit profile of user with id: {}", baseUser.getId());
        String profileName = dto.profileName();
        String about = dto.about();
        String location = dto.location();

        if (profileName != null && !profileName.trim().isEmpty()) {
            baseUser.setProfileName(profileName.trim());
        }
        if (about != null && !about.trim().isEmpty()) {
            baseUser.setAbout(about.trim());
        } 
        if (location != null && !location.trim().isEmpty()) {
            baseUser.setLocation(location.trim());
        }

        baseUserRepository.save(baseUser);
        return new StatusResponseDTO(true, "Successfully edit profile");
    }
}
