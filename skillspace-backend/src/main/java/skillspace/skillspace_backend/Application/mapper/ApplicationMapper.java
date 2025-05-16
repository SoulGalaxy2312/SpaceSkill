package skillspace.skillspace_backend.Application.mapper;

import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Job.mapper.JobMapper;

public class ApplicationMapper {
    
    public static ApplicationResponseDTO toApplicationResponseDTO(Application entity) {
        return new ApplicationResponseDTO(
            entity.getId(), 
            JobMapper.toJobResponseDTO(entity.getJob()),
            entity.getAppliedAt(), 
            entity.getResumeUrl());
    }
}
