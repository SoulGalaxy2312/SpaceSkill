package skillspace.skillspace_backend.auth.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Email " + email + " is already used");
    }
}
