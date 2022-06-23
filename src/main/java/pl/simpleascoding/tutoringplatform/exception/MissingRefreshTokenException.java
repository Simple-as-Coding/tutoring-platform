package pl.simpleascoding.tutoringplatform.exception;

public class MissingRefreshTokenException extends RuntimeException {

    public MissingRefreshTokenException() {
        super("Missing refresh token");
    }

}
