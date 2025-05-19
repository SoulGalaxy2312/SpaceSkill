package skillspace.skillspace_backend.Application.mapper;

import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.Application.response.ApplicationResponseDTO;
import skillspace.skillspace_backend.Job.mapper.JobMapper;
import skillspace.skillspace_backend.User.mapper.UserMapper;

public class ApplicationMapper {
    
    public static ApplicationResponseDTO toApplicationResponseDTO(Application entity) {
        return new ApplicationResponseDTO(
            entity.getId(), 
            JobMapper.toJobApplicationDTO(entity.getJob()),
            UserMapper.toUserBriefDTO(entity.getUser()),
            entity.getAppliedAt(), 
            entity.getResumeUrl());
    }
}
