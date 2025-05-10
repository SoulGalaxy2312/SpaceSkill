package skillspace.skillspace_backend.User.response;

import java.time.YearMonth;
import java.util.UUID;

import skillspace.skillspace_backend.shared.enums.EducationDegree;

public record EducationDTO(
    UUID id,
    YearMonth startDate,
    YearMonth endDate,
    String university,
    EducationDegree degree,
    String title
) {    
}
