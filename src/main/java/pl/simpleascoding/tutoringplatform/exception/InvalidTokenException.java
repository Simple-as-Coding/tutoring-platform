package pl.simpleascoding.tutoringplatform.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Invalid token");
    }

}
