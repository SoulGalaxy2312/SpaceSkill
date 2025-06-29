package skillspace.skillspace_backend.Notification.service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Notification.model.Notification;
import skillspace.skillspace_backend.Notification.repository.NotificationRepository;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class NotificationWriteServiceImpl implements NotificationWriteService {

    private final NotificationRepository notificationRepository;
    private final SecurityService securityService;

    public NotificationWriteServiceImpl(
        NotificationRepository notificationRepository,
        SecurityService securityService) {

        this.notificationRepository = notificationRepository;
        this.securityService = securityService;
    }

    private String getJobPostingTitle(String senderProfileName, String jobTitle) {
        return senderProfileName + " is hiring for " + jobTitle;
    }

    @Async
    public void sendNotification(BaseUser sender, User recipient, Job job) {        
        Notification notification = new Notification();
        String jobPostingTitle = getJobPostingTitle(sender.getProfileName(), job.getTitle());
        
        notification.setTitle(jobPostingTitle);

        notification.setSender(sender);
        notification.setRecipient(recipient);

        notification.setRead(false);

        notificationRepository.save(notification);
        log.info("Save notification for recipient {} successfully", recipient.getProfileName());
    }

    @Async
    public void sendNotification(BaseUser sender, List<User> recipients, Job job) {
        for (User recipient : recipients) {
            Notification notification = new Notification();
            String jobPostingTitle = getJobPostingTitle(sender.getProfileName(), job.getTitle());
            notification.setTitle(jobPostingTitle);

            notification.setSender(sender);
            notification.setRecipient(recipient);

            notification.setRead(false);

            notificationRepository.save(notification);
            log.info("Save notification for recipient {} successfully", recipient.getProfileName());
        }
    }

    @Transactional
    public StatusResponseDTO markNotificationAsRead(UUID id) {
        Notification notification = notificationRepository.getNotificationByIdOrElseThrow(id);
        UUID currentUserId = securityService.getCurrentBaseUserId();
        if (!notification.getRecipient().getId().equals(currentUserId)) {
            throw new AccessDeniedException("Current user is not allowed to mark the notification as read");
        }

        int isMarkedAsRead = notificationRepository.markNotificationAsRead(id, currentUserId);
        if (isMarkedAsRead == 0) {
            throw new IllegalArgumentException("Error marking notification as read");
        }
        return new StatusResponseDTO(true, "Successfully mark notification as read");
    }

    @Transactional
    public StatusResponseDTO deleteNotification(UUID id) {
        Notification notification = notificationRepository.getNotificationByIdOrElseThrow(id);
        UUID currentUserId = securityService.getCurrentBaseUserId();
        if (!notification.getRecipient().getId().equals(currentUserId)) {
            throw new AccessDeniedException("Current user is not allowed to delete the notification");
        }

        notificationRepository.delete(notification);
        return new StatusResponseDTO(true, "Successfully delete notification");
    }
}
