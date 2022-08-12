package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameTakenException extends IllegalArgumentException {
    public UsernameTakenException(String username) {
        super("Username \"" + username + "\" is already taken");
    }

}
