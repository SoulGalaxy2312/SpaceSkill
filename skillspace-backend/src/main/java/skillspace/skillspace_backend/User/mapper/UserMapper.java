package skillspace.skillspace_backend.User.mapper;

import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.response.UserProfileDTO;

public class UserMapper {
    
    public static UserProfileDTO toUserProfileDTO(User user) {
        return new UserProfileDTO(
            user.getProfileName(), 
            user.getLocation(), 
            user.getAbout(), 
            user.getSkills(), 
            user.getExperiences(), 
            user.getEducations());
    }
}
