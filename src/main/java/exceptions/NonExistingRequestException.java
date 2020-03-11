package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NonExistingRequestException extends RuntimeException {

    public NonExistingRequestException(final String message) {
        super(message);
    }

    public NonExistingRequestException() {

    }
}