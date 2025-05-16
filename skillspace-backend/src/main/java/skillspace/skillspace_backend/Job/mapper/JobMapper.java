package skillspace.skillspace_backend.Job.mapper;

import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.response.CreateJobResponseDTO;

public class JobMapper {
    
    public static CreateJobResponseDTO toCreateJobDTO(Job entity) {
        return new CreateJobResponseDTO(
            entity.getId(), 
            entity.getTitle(), 
            entity.getRequiredSkills(), 
            entity.getDescription(), 
            entity.getCreatedAt());
    }
}
