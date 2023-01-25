package pl.simpleascoding.tutoringplatform.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailTakenException extends IllegalArgumentException {
    private static final String MESSAGE_EMAIL_IS_ALREADY_IN_USE = "Email %s is already in use";

    public EmailTakenException(String email) {
        super(String.format(MESSAGE_EMAIL_IS_ALREADY_IN_USE, email));
    }
}
