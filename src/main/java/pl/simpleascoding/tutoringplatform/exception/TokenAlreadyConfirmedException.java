package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TokenAlreadyConfirmedException extends IllegalArgumentException {
    public TokenAlreadyConfirmedException() {
        super("Token already confirmed");
    }
}
