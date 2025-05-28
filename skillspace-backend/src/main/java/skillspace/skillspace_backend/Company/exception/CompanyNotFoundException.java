package skillspace.skillspace_backend.Company.exception;

import java.util.UUID;

import skillspace.skillspace_backend.shared.exception.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(UUID id) {
        super("Company with id " + id.toString() + " was not found");
    }

    public CompanyNotFoundException(String email) {
        super("Company with email " + email + " was not found");
    }
}
