package skillspace.skillspace_backend.Company.mapper;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.response.CompanyProfileDTO;

public class CompanyMapper {
    
    public static CompanyProfileDTO toCompanyProfileDTO(Company entity) {
        return new CompanyProfileDTO(
            entity.getId(),
            entity.getProfileName(),
            entity.getLocation(),
            entity.getAbout());
    }
}
