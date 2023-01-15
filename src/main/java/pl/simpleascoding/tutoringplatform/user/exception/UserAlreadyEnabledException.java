package pl.simpleascoding.tutoringplatform.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyEnabledException extends IllegalArgumentException {
    private static final String MESSAGE_USER_IS_ALREADY_ENABLED = "User %s is already enabled";

    public UserAlreadyEnabledException(String username) {
        super(String.format(MESSAGE_USER_IS_ALREADY_ENABLED, username));
    }
}
