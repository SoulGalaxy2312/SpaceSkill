package skillspace.skillspace_backend.Job.exception;

import java.util.UUID;

import skillspace.skillspace_backend.shared.exception.NotFoundException;

public class JobNotFoundException extends NotFoundException {
    public JobNotFoundException(UUID jobId) {
        super("Job with id " + jobId.toString() + " was not found");
    }
}
