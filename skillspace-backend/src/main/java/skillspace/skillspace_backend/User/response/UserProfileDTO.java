package skillspace.skillspace_backend.User.response;

import java.util.List;

import skillspace.skillspace_backend.shared.response.BaseUserProfileDTO;

public record UserProfileDTO(
    BaseUserProfileDTO baseUserProfileInformation,
    List<String> skills,
    List<ExperienceDTO> experiences,
    List<EducationDTO> educations
) {
}