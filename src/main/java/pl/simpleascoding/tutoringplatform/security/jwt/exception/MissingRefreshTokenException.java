package pl.simpleascoding.tutoringplatform.security.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRefreshTokenException extends IllegalArgumentException {
    private static final String MESSAGE_MISSING_REFRESH_TOKEN = "Missing refresh token";

    public MissingRefreshTokenException() {
        super(MESSAGE_MISSING_REFRESH_TOKEN);
    }

}
