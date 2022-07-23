package pl.simpleascoding.tutoringplatform.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String ERROR_WITH_ID = "User with id %d not found";
    private static final String ERROR_WITH_USERNAME = "User %s not found";

    public UserNotFoundException(long id) {
        super(String.format(ERROR_WITH_ID, id));
    }

    public UserNotFoundException(String username){
        super(String.format(ERROR_WITH_USERNAME, username));
    }
}
