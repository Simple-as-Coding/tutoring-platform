package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserIsNotATeacherException extends IllegalArgumentException {

    public UserIsNotATeacherException() {
        super("User is not a teacher");
    }

}
