package skillspace.skillspace_backend.Job.mapper;

import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.response.JobResponseDTO;

public class JobMapper {
    
    public static JobResponseDTO toJobResponseDTO(Job entity) {
        return new JobResponseDTO(
            entity.getId(), 
            entity.getTitle(), 
            entity.getRequiredSkills(), 
            entity.getDescription(), 
            entity.getCreatedAt());
    }
}
