package skillspace.skillspace_backend.User.model;

import java.time.YearMonth;
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
import skillspace.skillspace_backend.shared.enums.EducationDegree;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_educations  ")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private YearMonth startDate;
    private YearMonth endDate;
    private String university;

    @Enumerated(EnumType.STRING)
    private EducationDegree degree;

    private String title;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
