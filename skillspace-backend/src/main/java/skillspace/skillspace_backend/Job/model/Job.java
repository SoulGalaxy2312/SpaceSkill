package skillspace.skillspace_backend.Job.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillspace.skillspace_backend.Application.model.Application;
import skillspace.skillspace_backend.Company.model.Company;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "job")
    private Set<Application> applications;

    private String title;

    private Set<String> requiredSkills = new HashSet<>();

    private String description;

    private LocalDate createdAt;

    private String location;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }
}
