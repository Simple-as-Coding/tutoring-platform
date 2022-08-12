package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRefreshTokenException extends IllegalArgumentException {
    public MissingRefreshTokenException() {
        super("Missing refresh token");
    }

}
