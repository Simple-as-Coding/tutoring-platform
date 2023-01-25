package pl.simpleascoding.tutoringplatform.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends IllegalArgumentException {
    private static final String MESSAGE_USER_WITH_ID_NOT_FOUND = "User with id %d not found";
    private static final String MESSAGE_USER_WITH_USERNAME_NOT_FOUND = "User %s not found";

    public UserNotFoundException(long id) {
        super(String.format(MESSAGE_USER_WITH_ID_NOT_FOUND, id));
    }

    public UserNotFoundException(String username) {
        super(String.format(MESSAGE_USER_WITH_USERNAME_NOT_FOUND, username));
    }
}
