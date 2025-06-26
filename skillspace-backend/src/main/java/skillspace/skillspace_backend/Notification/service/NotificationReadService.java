package skillspace.skillspace_backend.Notification.service;

import skillspace.skillspace_backend.Notification.response.NotificationResponseDTO;
import skillspace.skillspace_backend.shared.response.PagingDTO;

public interface NotificationReadService {
    PagingDTO<NotificationResponseDTO> getNotifications(int page, int size) throws IllegalArgumentException;
}
