package skillspace.skillspace_backend.User.request;

public record UserRegisterDTO(
    String email,
    String password, 
    String firstName,
    String lastName,
    String location
) {
    
}
