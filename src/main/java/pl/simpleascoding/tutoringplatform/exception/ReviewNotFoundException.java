package pl.simpleascoding.tutoringplatform.exception;

public class ReviewNotFoundException extends RuntimeException{

    private static final String ERROR = "Review with id %d not found";

    public ReviewNotFoundException(long id){
        super(String.format(ERROR, id));
    }
}
