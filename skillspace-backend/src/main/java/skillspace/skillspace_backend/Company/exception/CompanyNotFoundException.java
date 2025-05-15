package skillspace.skillspace_backend.Company.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String msg) {
        super(msg);
    }
}
