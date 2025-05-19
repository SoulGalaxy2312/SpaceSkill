package skillspace.skillspace_backend.User.mapper;

import java.util.stream.Collectors;

import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.response.UserApplicationDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public class UserMapper {
    
    public static UserProfileDTO toUserProfileDTO(User user) {
        return new UserProfileDTO(
            user.getId(),
            user.getProfileName(), 
            user.getLocation(), 
            user.getAbout(), 
            user.getSkills(), 
            user.getExperiences().stream()
                                .map(ExperienceMapper::toExperienceDTO)
                                .collect(Collectors.toList()), 
            user.getEducations().stream()
                                .map(EducationMapper::toEducationDTO)
                                .collect(Collectors.toList()));
    }

    public static UserApplicationDTO toUserApplicationDTO(User user) {
        return new UserApplicationDTO(
            user.getId(),
            user.getProfileName()
        );
    }
}
