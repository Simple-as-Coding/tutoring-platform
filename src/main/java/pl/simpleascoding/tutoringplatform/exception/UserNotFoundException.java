package pl.simpleascoding.tutoringplatform.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String ERROR_WITH_ID = "User with id %d not found";

    public UserNotFoundException(Long id) {
        super(String.format(ERROR_WITH_ID, id));
    }
}
