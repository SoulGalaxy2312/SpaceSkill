package skillspace.skillspace_backend.User.exception;

import java.util.UUID;

import skillspace.skillspace_backend.shared.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(UUID userId) {
        super("User with ID " + userId + " was not found.");
    }

    public UserNotFoundException(String email) {
        super("User with email " + email + " was not found");
    }
}
