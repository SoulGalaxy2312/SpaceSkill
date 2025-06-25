package skillspace.skillspace_backend.Notification.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.Notification.model.Notification;
import skillspace.skillspace_backend.Notification.repository.NotificationRepository;
import skillspace.skillspace_backend.User.model.User;

@Service
@Slf4j
public class NotificationWriteServiceImpl implements NotificationWriteService {
    private final NotificationRepository notificationRepository;

    public NotificationWriteServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    private String getJobPostingTitle(String senderProfileName, String jobTitle) {
        return senderProfileName + " is hiring for " + jobTitle;
    }

    @Async
    public void sendNotification(BaseUser sender, User recipient, Job job) {        
        Notification notification = new Notification();
        String jobPostingTitle = getJobPostingTitle(sender.getProfileName(), job.getTitle());
        notification.setTitle(jobPostingTitle);

        notification.setSenderId(sender.getId());
        notification.setSenderProfileName(sender.getProfileName());
        notification.setSenderRole(sender.getRole());

        notification.setRecipientId(recipient.getId());
        notification.setRecipientRole(recipient.getRole());
        notification.setRecipientProfileName(recipient.getProfileName());
        
        notification.setJob(job);
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

            notification.setSenderId(sender.getId());
            notification.setSenderProfileName(sender.getProfileName());
            notification.setSenderRole(sender.getRole());

            notification.setRecipientId(recipient.getId());
            notification.setRecipientProfileName(recipient.getProfileName());
            notification.setRecipientRole(recipient.getRole());

            notification.setJob(job);
            notification.setRead(false);

            notificationRepository.save(notification);
            log.info("Save notification for recipient {} successfully", recipient.getProfileName());
        }
    }
}
