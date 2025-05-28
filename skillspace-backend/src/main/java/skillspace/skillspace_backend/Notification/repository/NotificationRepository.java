package skillspace.skillspace_backend.Notification.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skillspace.skillspace_backend.Notification.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>{
    
}
