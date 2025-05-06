package skillspace.skillspace_backend.User.model;

import java.time.YearMonth;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Experience {
    private YearMonth startDate;
    private YearMonth endDate;
    private String company;
    private String title;
}
