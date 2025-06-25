package skillspace.skillspace_backend.BaseUser.exception;

import skillspace.skillspace_backend.shared.exception.NotFoundException;

public class BaseUserNotFoundException extends NotFoundException {
    public BaseUserNotFoundException(String message) {
        super(message);
    }
}
