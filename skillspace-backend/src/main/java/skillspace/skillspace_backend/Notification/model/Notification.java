package skillspace.skillspace_backend.Notification.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.shared.enums.UserRole;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String message;

    private UUID senderId;
    @Enumerated(EnumType.STRING)
    private UserRole senderRole;

    private UUID recipientId;
    @Enumerated(EnumType.STRING)
    private UserRole recipientRole;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "job_id")
    private Job job;

    private boolean isRead;

    private String url;
}
