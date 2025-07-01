package skillspace.skillspace_backend.Application.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillspace.skillspace_backend.Job.model.Job;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.enums.ApplicationStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDate appliedAt;

    @Column(nullable = false)
    private String personalStatement;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "reviewer_note")
    private String reviewerNote;
    
    @PrePersist
    public void prePersist() {
        this.appliedAt = LocalDate.now();
    }
}
