package fm.example.personal_finance_manager.config;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
