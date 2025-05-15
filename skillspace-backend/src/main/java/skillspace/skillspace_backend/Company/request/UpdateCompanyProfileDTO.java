package skillspace.skillspace_backend.Company.request;

public record UpdateCompanyProfileDTO(
    String profileName,
    String location,
    String about
) {
}