package skillspace.skillspace_backend.Notification.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Notification.mapper.NotificationMapper;
import skillspace.skillspace_backend.Notification.model.Notification;
import skillspace.skillspace_backend.Notification.repository.NotificationRepository;
import skillspace.skillspace_backend.Notification.response.NotificationResponseDTO;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
public class NotificationReadServiceImpl implements NotificationReadService {
    private final NotificationRepository notificationRepository;
    private final SecurityService securityService;

    public NotificationReadServiceImpl(NotificationRepository notificationRepository, SecurityService securityService) {
        this.notificationRepository = notificationRepository;
        this.securityService = securityService;
    }
    
    public Page<NotificationResponseDTO> getNotifications(int page, int size) throws IllegalArgumentException {
        User user = securityService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if (page < 0 || page >= pageable.getPageSize()) {
            throw new IllegalArgumentException("The page is not accepted");
        }

        Page<Notification> notifications = notificationRepository.findAllByRecipientIdOrderByCreatedAtDesc(user.getId(), pageable);

        Page<NotificationResponseDTO> response = notifications.map(NotificationMapper::toNotificationResponseDTO);
        return response;
    }
}
