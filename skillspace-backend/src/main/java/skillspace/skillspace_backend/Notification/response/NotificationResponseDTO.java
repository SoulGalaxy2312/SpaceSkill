package skillspace.skillspace_backend.Notification.response;

import java.util.UUID;

import skillspace.skillspace_backend.shared.model.BaseUserBrief;

public record NotificationResponseDTO(
    UUID id,
    String title,
    String message,
    BaseUserBrief sender,
    boolean isRead,
    String url
) {
    
}
