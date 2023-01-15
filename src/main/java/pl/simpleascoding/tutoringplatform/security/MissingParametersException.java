package pl.simpleascoding.tutoringplatform.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingParametersException extends IllegalArgumentException {
    private static final String MESSAGE_MISSING_PARAMETERS = "Missing parameters";

    public MissingParametersException() {
        super(MESSAGE_MISSING_PARAMETERS);
    }

}
