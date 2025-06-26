package skillspace.skillspace_backend.Notification.controller;

import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Notification.response.NotificationResponseDTO;
import skillspace.skillspace_backend.Notification.service.NotificationReadService;
import skillspace.skillspace_backend.shared.constants.ApiPath;
import skillspace.skillspace_backend.shared.response.PagingDTO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class NotificationReadController {
    private final NotificationReadService notificationReadService;

    public NotificationReadController(NotificationReadService notificationReadService) {
        this.notificationReadService = notificationReadService;
    } 
    
    @GetMapping(ApiPath.NOTIFICATION + "/notifications")
    @PreAuthorize("hasRole('USER')")
    public PagingDTO<NotificationResponseDTO> getNotifications(
        @RequestParam(name = "page", required = false ,defaultValue = "0") int page,
        @RequestParam(name = "size", required = false ,defaultValue = "10") int size
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
        return notificationReadService.getNotifications(page - 1, size);
    }
    
}
