package skillspace.skillspace_backend.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
    @NotBlank(message = "Email must not be blank")
    String email,

    @NotBlank(message = "Password must not be blank")
    String password, 

    String profileName,
    String location,
    boolean isCompany
) {    
}
