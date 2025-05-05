package skillspace.skillspace_backend.User.response;

import java.util.List;

import skillspace.skillspace_backend.User.model.Education;
import skillspace.skillspace_backend.User.model.Experience;

public record UserProfileDTO(
    String profileName,
    String location,
    String about,
    List<String> skills,
    List<Experience> experiences,
    List<Education> educations
) {
}