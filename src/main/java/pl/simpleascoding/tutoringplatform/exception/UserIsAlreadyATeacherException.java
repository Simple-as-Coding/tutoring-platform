package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserIsAlreadyATeacherException extends IllegalArgumentException {

    private static final String MESSAGE_USER_ALREADY_A_TEACHER = "User is already a teacher";

    public UserIsAlreadyATeacherException() {
        super(MESSAGE_USER_ALREADY_A_TEACHER);
    }
}
