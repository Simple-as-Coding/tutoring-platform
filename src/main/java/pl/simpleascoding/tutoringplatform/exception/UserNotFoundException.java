package pl.simpleascoding.tutoringplatform.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE_USER_WITH_ID_NOT_FOUND = "User with id %d not found";
    private static final String MESSAGE_USER_WITH_USERNAME_NOT_FOUND = "User %s not found";

    public UserNotFoundException(long id) {
        super(String.format(MESSAGE_USER_WITH_ID_NOT_FOUND, id));
    }

    public UserNotFoundException(String username) {
        super(String.format(MESSAGE_USER_WITH_USERNAME_NOT_FOUND, username));
    }
}
