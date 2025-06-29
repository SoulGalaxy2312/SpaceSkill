package skillspace.skillspace_backend.Notification.response;

import java.time.LocalDateTime;
import java.util.UUID;

import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;

public record NotificationResponseDTO(
    UUID id,
    String title,
    String message,
    BaseUserBrief sender,
    boolean isRead,
    LocalDateTime createdAt
) {
    
}
