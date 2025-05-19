package skillspace.skillspace_backend.Job.mapper;

import skillspace.skillspace_backend.Company.mapper.CompanyMapper;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Job.request.JobApplicationDTO;
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

    public static JobApplicationDTO toJobApplicationDTO(Job entity) {
        return new JobApplicationDTO(
            entity.getId(),
            entity.getTitle(),
            CompanyMapper.toCompanyApplicationDTO(entity.getCompany())
        );
    }
}