package skillspace.skillspace_backend.Notification.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.Notification.service.NotificationWriteService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(ApiPath.NOTIFICATION)
@Slf4j
public class NotificationWriteController {

    private final NotificationWriteService notificationWriteService;

    public NotificationWriteController(NotificationWriteService notificationWriteService) {
        this.notificationWriteService = notificationWriteService;
    }

    @PutMapping("/{id}/mark-read")
    @PreAuthorize("hasRole('USER')")
    public StatusResponseDTO markNotificationAsRead(@PathVariable UUID id) {
        log.info("Attempting to mark notification as read with id: {}", id);
        return notificationWriteService.markNotificationAsRead(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public StatusResponseDTO deleteNotification(@PathVariable UUID id) {
        log.info("Attempting to delete notification with id: {}", id);
        return notificationWriteService.deleteNotification(id);
    }


}
