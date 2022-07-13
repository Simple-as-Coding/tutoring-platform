package pl.simpleascoding.tutoringplatform.exception;

public class TokenAlreadyConfirmedException extends RuntimeException {
    public TokenAlreadyConfirmedException() {
        super("Token already confirmed");
    }
}
