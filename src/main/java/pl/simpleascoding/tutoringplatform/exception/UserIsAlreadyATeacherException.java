package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserIsAlreadyATeacherException extends IllegalArgumentException {
    public UserIsAlreadyATeacherException() {
        super("This is already a teacher");
    }
}
