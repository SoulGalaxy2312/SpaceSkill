package skillspace.skillspace_backend.Notification.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import skillspace.skillspace_backend.Notification.exception.NotificationNotFoundException;
import skillspace.skillspace_backend.Notification.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>{
    default Notification getNotificationByIdOrElseThrow(UUID id) {
        return findById(id).orElseThrow(() -> {
                throw new NotificationNotFoundException(id);
            });
    }

    @Query(
        value = """
                    SELECT n
                    FROM Notification n
                    WHERE n.recipient.id = :recipientId
                    ORDER BY n.createdAt DESC
                """
    )
    Page<Notification> findAllByRecipientIdOrderByCreatedAtDesc(UUID recipientId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(
        value = """
                    UPDATE Notification
                    SET isRead = true
                    WHERE id = :id
                    AND recipient.id = :recipientId
                """
    )
    int markNotificationAsRead(UUID id, UUID recipientId);
}
