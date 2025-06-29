package skillspace.skillspace_backend.Notification.exception;

import java.util.UUID;

import skillspace.skillspace_backend.shared.exception.NotFoundException;

public class NotificationNotFoundException extends NotFoundException {
    public NotificationNotFoundException(UUID id)  {
        super("Notification was not found with id: " + id);
    }
}
