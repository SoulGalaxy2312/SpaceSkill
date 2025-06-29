package skillspace.skillspace_backend.Notification.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import skillspace.skillspace_backend.Notification.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>{

    @Query(
        value = """
                    SELECT n
                    FROM Notification n
                    WHERE n.recipient.id = :recipientId
                    ORDER BY n.createdAt DESC
                """
    )
    Page<Notification> findAllByRecipientIdOrderByCreatedAtDesc(UUID recipientId, Pageable pageable);
}
