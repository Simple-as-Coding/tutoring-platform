package pl.simpleascoding.tutoringplatform.security.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TokenAlreadyConfirmedException extends IllegalArgumentException {
    private static final String MESSAGE_TOKEN_ALREADY_CONFIRMED = "Token already confirmed";

    public TokenAlreadyConfirmedException() {
        super(MESSAGE_TOKEN_ALREADY_CONFIRMED);
    }
}
