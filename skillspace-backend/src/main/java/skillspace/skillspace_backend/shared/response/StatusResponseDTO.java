package skillspace.skillspace_backend.shared.response;

public class StatusResponseDTO {
    private boolean success;
    private String message;

    public StatusResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
