package skillspace.skillspace_backend.User.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Experience {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String company;
    private String title;
}
