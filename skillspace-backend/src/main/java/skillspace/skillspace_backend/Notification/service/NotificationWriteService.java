package skillspace.skillspace_backend.Notification.service;

import java.util.List;

import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.model.BaseUser;

public interface NotificationWriteService {
    void sendNotification(BaseUser sender, User recipient, Job job);
    void sendNotification(BaseUser sender, List<User> recipients, Job job);
}
