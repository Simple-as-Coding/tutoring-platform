package pl.simpleascoding.tutoringplatform.exception;

public class UserAlreadyEnabledException extends RuntimeException {
    public UserAlreadyEnabledException(String username) {
        super("User \"" + username + "\" is already enabled");
    }
}
