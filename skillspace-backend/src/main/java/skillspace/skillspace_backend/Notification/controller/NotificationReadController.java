package skillspace.skillspace_backend.Notification.controller;

import org.springframework.web.bind.annotation.RestController;

import skillspace.skillspace_backend.Notification.response.NotificationResponseDTO;
import skillspace.skillspace_backend.Notification.service.NotificationReadService;
import skillspace.skillspace_backend.shared.constants.ApiPath;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class NotificationReadController {
    private final NotificationReadService notificationReadService;

    public NotificationReadController(NotificationReadService notificationReadService) {
        this.notificationReadService = notificationReadService;
    } 
    
    @GetMapping(ApiPath.NOTIFICATION + "/getNotifications/{page}")
    @PreAuthorize("hasRole('USER')")
    public List<NotificationResponseDTO> getMethodName(@PathVariable int page) {
        return notificationReadService.getNotifications(page - 1).getContent();
    }
    
}
