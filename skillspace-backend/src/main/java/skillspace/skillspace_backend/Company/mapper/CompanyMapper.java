package skillspace.skillspace_backend.Company.mapper;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.response.CompanyApplicationDTO;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;

public class CompanyMapper {
    
    public static CompanyProfileDTO toCompanyProfileDTO(Company entity, boolean isCurrentCompany) {
        return new CompanyProfileDTO(
            entity.getId(),
            entity.getProfileName(),
            entity.getLocation(),
            entity.getAbout(),
            isCurrentCompany);
    }

    public static CompanyApplicationDTO toCompanyApplicationDTO(Company entity) {
        return new CompanyApplicationDTO(
            entity.getId(), 
            entity.getProfileName());
    }
}
