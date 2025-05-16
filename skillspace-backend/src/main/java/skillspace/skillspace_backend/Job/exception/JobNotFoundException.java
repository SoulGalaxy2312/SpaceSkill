package skillspace.skillspace_backend.Job.exception;

import java.util.UUID;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(UUID msg) {
        super(msg.toString());
    }
}
