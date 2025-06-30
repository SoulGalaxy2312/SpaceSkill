package skillspace.skillspace_backend.BaseUser.request;

public record EditProfileRequestDTO(
    String profileName,
    String location,
    String about
) {

}