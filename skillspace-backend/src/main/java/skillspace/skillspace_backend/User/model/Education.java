package skillspace.skillspace_backend.User.model;

import java.time.YearMonth;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import skillspace.skillspace_backend.shared.enums.EducationDegree;

@Data
@Embeddable
public class Education {
    private YearMonth startDate;
    private YearMonth endDate;
    private String university;

    @Enumerated(EnumType.STRING)
    private EducationDegree degree;
}
