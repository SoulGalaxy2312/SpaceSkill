package skillspace.skillspace_backend.User.exception;

public class UsernameExistsException extends RuntimeException {
    
    public UsernameExistsException() {
        super("A user with this email already exists");
    }

    public UsernameExistsException(String message) {
        super(message);
    }
}
