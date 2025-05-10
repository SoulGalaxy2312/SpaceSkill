package skillspace.skillspace_backend.User.mapper;

import skillspace.skillspace_backend.User.model.Education;
import skillspace.skillspace_backend.User.response.EducationDTO;

public class EducationMapper {
    
    public static EducationDTO toEducationDTO(Education entity) {
        return new EducationDTO(
            entity.getId(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getUniversity(),
            entity.getDegree(),
            entity.getTitle());
    }
}
