package skillspace.skillspace_backend.User.model;

import java.time.YearMonth;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_experiences")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private YearMonth startDate;
    private YearMonth endDate;
    private String company;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
