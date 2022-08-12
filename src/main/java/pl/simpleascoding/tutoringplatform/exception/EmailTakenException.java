package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailTakenException extends IllegalArgumentException {
    public EmailTakenException(String email) {
        super("Email \"" + email + "\" is already in use");
    }
}
