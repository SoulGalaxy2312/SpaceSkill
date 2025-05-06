package skillspace.skillspace_backend.User.request;

import java.time.YearMonth;

public record AddExperienceDTO(
    YearMonth startDate,
    YearMonth endDate,
    String company,
    String title
) {
    
}
