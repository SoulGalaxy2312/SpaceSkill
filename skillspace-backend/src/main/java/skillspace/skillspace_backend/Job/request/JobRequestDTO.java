package skillspace.skillspace_backend.Job.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record JobRequestDTO (
    @NotBlank(message = "Title must not be blank")
    String title,

    @NotEmpty(message = "Required Skills must have at least one skill")
    Set<@NotBlank(message="Skill cannot be blank") String> requiredSkills,

    @NotBlank(message = "Description must not be blank")
    String description
) {
    
}
