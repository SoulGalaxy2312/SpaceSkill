package skillspace.skillspace_backend.User.mapper;

import java.util.stream.Collectors;

import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.response.BaseUserProfileDTO;

public class UserMapper {
    
    public static UserProfileDTO toUserProfileDTO(User user, boolean isCurrentBaseUser, boolean isFollowedByCurrentBaseUser) {
        return new UserProfileDTO(
            new BaseUserProfileDTO(
                user.getId(), 
                user.getProfileName(),
                user.getLocation(),
                user.getAbout(),
                UserRole.USER,
                isCurrentBaseUser,
                isFollowedByCurrentBaseUser
            ),
            user.getSkills(), 
            user.getExperiences().stream()
                                .map(ExperienceMapper::toExperienceDTO)
                                .collect(Collectors.toList()), 
            user.getEducations().stream()
                                .map(EducationMapper::toEducationDTO)
                                .collect(Collectors.toList())
        );
    }
}
