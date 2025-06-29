package skillspace.skillspace_backend.Notification.mapper;

import skillspace.skillspace_backend.BaseUser.mapper.BaseUserMapper;
import skillspace.skillspace_backend.Notification.model.Notification;
import skillspace.skillspace_backend.Notification.response.NotificationResponseDTO;

public class NotificationMapper {
    
    public static NotificationResponseDTO toNotificationResponseDTO(Notification entity) {
        return new NotificationResponseDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getMessage(),
            BaseUserMapper.toBaseUserBrief(entity.getSender()),
            entity.isRead(),
            entity.getCreatedAt()
        );
    }
}
