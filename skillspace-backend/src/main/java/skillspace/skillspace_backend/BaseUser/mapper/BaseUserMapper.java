package skillspace.skillspace_backend.BaseUser.mapper;

import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;

public class BaseUserMapper {
    
    public static BaseUserBrief toBaseUserBrief(BaseUser user) {
        return new BaseUserBrief(
            user.getId(),
            user.getProfileName(),
            user.getRole()
        );
    }
}
