package skillspace.skillspace_backend.Admin.mapper;

import skillspace.skillspace_backend.Admin.response.AdminBaseUserBrief;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;

public class AdminBaseUserBriefMapper {
    public static AdminBaseUserBrief toAdminBaseUserBrief(BaseUser baseUser) {
        if (baseUser == null) {
            return null;
        }
        
        AdminBaseUserBrief adminBaseUserBrief = new AdminBaseUserBrief();
        adminBaseUserBrief.setId(baseUser.getId());
        adminBaseUserBrief.setProfileName(baseUser.getProfileName());
        adminBaseUserBrief.setEmail(baseUser.getEmail());
        adminBaseUserBrief.setRole(baseUser.getRole());
        
        return adminBaseUserBrief;
    }
}
