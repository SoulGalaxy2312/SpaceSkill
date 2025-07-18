package skillspace.skillspace_backend.Job.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class JobSearchRequestDTO {
    private String title;
    private String location;
    private String skill;
    private String company;

    @Min(value = 1, message = "Page number must be at least 1")
    private int page = 1;

    @Min(value = 1, message = "Page size must be at least 1")
    private int size = 10;
}