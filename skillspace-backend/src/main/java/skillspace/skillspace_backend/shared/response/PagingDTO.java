package skillspace.skillspace_backend.shared.response;

import java.util.List;

import lombok.Data;

@Data
public class PagingDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    
    public PagingDTO(List<T> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page + 1; // Convert to 1-based index for user-friendliness
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
