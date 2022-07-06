package pl.simpleascoding.tutoringplatform.exception;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String email) {
        super("Email \"" + email + "\" is already in use");
    }
}
