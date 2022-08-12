package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends IllegalArgumentException {
    public InvalidTokenException() {
        super("Invalid token");
    }

}
