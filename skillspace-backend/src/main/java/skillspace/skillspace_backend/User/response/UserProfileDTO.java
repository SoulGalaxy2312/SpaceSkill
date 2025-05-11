package skillspace.skillspace_backend.User.response;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record UserProfileDTO(
    UUID id,
    String profileName,
    String location,
    String about,
    Set<String> skills,
    List<ExperienceDTO> experiences,
    List<EducationDTO> educations
) {
}