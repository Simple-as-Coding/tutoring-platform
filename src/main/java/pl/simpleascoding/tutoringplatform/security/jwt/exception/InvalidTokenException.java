package pl.simpleascoding.tutoringplatform.security.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends IllegalArgumentException {
    private static final String MESSAGE_INVALID_TOKEN = "Invalid token";

    public InvalidTokenException() {
        super(MESSAGE_INVALID_TOKEN);
    }

}
