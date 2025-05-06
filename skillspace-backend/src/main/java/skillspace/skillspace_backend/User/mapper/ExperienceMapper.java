package skillspace.skillspace_backend.User.mapper;

import skillspace.skillspace_backend.User.model.Experience;
import skillspace.skillspace_backend.User.response.ExperienceDTO;

public class ExperienceMapper {
    
    public static ExperienceDTO toExperienceDTO(Experience entity) {
        return new ExperienceDTO(
            entity.getId(), 
            entity.getStartDate(), 
            entity.getEndDate(), 
            entity.getCompany(), 
            entity.getTitle());
    }
}
