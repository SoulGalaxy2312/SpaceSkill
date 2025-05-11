package skillspace.skillspace_backend.User.exception;

public class DuplicateSkillException extends RuntimeException {
    public DuplicateSkillException(String msg) {
        super(msg);
    }
}
