package skillspace.skillspace_backend.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;

import skillspace.skillspace_backend.User.exception.DuplicateSkillException;
import skillspace.skillspace_backend.auth.exception.EmailAlreadyUsedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUsernameExistsException(EmailAlreadyUsedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DuplicateSkillException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleDuplicateSkillException(DuplicateSkillException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleJsonProcessingException(JsonProcessingException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }
}
