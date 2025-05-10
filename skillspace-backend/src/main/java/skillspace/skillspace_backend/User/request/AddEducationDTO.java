package skillspace.skillspace_backend.User.request;

import java.time.YearMonth;

import skillspace.skillspace_backend.shared.enums.EducationDegree;

public record AddEducationDTO(
    YearMonth startDate,
    YearMonth endDate,
    String university,
    EducationDegree degree
) {
    
}
