package skillspace.skillspace_backend.User.response;

import java.time.YearMonth;
import java.util.UUID;

public record ExperienceDTO(
    UUID id,
    YearMonth startDate,
    YearMonth endDate,
    String company,
    String title
) {
}