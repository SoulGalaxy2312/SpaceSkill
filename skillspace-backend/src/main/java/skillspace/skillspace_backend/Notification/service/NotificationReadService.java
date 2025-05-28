package skillspace.skillspace_backend.Notification.service;

import org.springframework.data.domain.Page;

import skillspace.skillspace_backend.Notification.response.NotificationResponseDTO;

public interface NotificationReadService {
    Page<NotificationResponseDTO> getNotifications(int page) throws IllegalArgumentException;
}
