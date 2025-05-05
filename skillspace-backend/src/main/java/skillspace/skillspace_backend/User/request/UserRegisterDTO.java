package skillspace.skillspace_backend.User.request;

public record UserRegisterDTO(
    String email,
    String password, 
    String profileName,
    String location
) {
    
}
