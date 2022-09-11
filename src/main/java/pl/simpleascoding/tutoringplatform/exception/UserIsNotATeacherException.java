package pl.simpleascoding.tutoringplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserIsNotATeacherException extends IllegalArgumentException {

    private static final String MESSAGE_USER_IS_NOT_A_TEACHER = "User is not a teacher";

    public UserIsNotATeacherException() {
        super(MESSAGE_USER_IS_NOT_A_TEACHER);
    }

}
