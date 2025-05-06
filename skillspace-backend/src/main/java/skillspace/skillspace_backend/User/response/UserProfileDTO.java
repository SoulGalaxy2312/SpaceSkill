package skillspace.skillspace_backend.User.response;

import java.util.List;
import java.util.UUID;

import skillspace.skillspace_backend.User.model.Education;

public record UserProfileDTO(
    UUID id,
    String profileName,
    String location,
    String about,
    List<String> skills,
    List<ExperienceDTO> experiences,
    List<Education> educations
) {
}