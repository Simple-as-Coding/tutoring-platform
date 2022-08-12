package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends IllegalArgumentException {
    public TokenNotFoundException() {
        super("Token not found");
    }
}
