package skillspace.skillspace_backend.Application.exception;

import java.util.UUID;

import skillspace.skillspace_backend.shared.exception.NotFoundException;

public class ApplicationNotFoundException extends NotFoundException {
    public ApplicationNotFoundException(UUID id) {
        super("Application with id: " + id + " was not found");
    } 
}
