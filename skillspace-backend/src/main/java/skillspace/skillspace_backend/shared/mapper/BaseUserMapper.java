package skillspace.skillspace_backend.shared.mapper;

import skillspace.skillspace_backend.shared.model.BaseUser;
import skillspace.skillspace_backend.shared.model.BaseUserBrief;

public class BaseUserMapper {
    
    public static BaseUserBrief toBaseUserBrief(BaseUser user) {
        return new BaseUserBrief(
            user.getId(),
            user.getProfileName(),
            user.getRole()
        );
    }
}
