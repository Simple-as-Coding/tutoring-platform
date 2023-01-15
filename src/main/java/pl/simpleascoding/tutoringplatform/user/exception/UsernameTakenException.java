package pl.simpleascoding.tutoringplatform.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameTakenException extends IllegalArgumentException {
    private static final String MESSAGE_USERNAME_IS_ALREADY_TAKEN = "Username %s is already taken";

    public UsernameTakenException(String username) {
        super(String.format(MESSAGE_USERNAME_IS_ALREADY_TAKEN, username));
    }

}
