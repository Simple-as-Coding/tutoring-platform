package pl.simpleascoding.tutoringplatform.exception;

public class MissingParametersException extends RuntimeException {

    public MissingParametersException() {
        super("Missing parameters");
    }

}
