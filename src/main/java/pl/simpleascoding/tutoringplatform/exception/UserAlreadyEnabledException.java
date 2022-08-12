package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyEnabledException extends IllegalArgumentException {
    public UserAlreadyEnabledException(String username) {
        super("User \"" + username + "\" is already enabled");
    }
}
