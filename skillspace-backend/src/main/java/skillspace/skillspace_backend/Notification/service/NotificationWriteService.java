package skillspace.skillspace_backend.Notification.service;

import java.util.List;
import java.util.UUID;

import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface NotificationWriteService {
    void sendNotification(BaseUser sender, User recipient, Job job);
    void sendNotification(BaseUser sender, List<User> recipients, Job job);

    StatusResponseDTO markNotificationAsRead(UUID id);
    StatusResponseDTO deleteNotification(UUID id);
}
