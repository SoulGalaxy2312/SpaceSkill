package skillspace.skillspace_backend.Company.mapper;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.response.CompanyApplicationDTO;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.response.BaseUserProfileDTO;

public class CompanyMapper {
    
    public static CompanyProfileDTO toCompanyProfileDTO(Company entity, boolean isCurrentCompany, boolean isFollowedByCurrentBaseUser) {
        return new CompanyProfileDTO(
            new BaseUserProfileDTO(
                entity.getId(),
                entity.getProfileName(),
                entity.getLocation(),
                entity.getAbout(),
                UserRole.COMPANY,
                isCurrentCompany,
                isFollowedByCurrentBaseUser
            ));
    }

    public static CompanyApplicationDTO toCompanyApplicationDTO(Company entity) {
        return new CompanyApplicationDTO(
            entity.getId(), 
            entity.getProfileName());
    }
}
