package pl.simpleascoding.tutoringplatform.exception;

public class ReviewNotFoundException extends RuntimeException{

    private static final String MESSAGE_REVIEW_WITH_ID_NOT_FOUND = "Review with id %d not found";

    public ReviewNotFoundException(long id){
        super(String.format(MESSAGE_REVIEW_WITH_ID_NOT_FOUND, id));
    }
}
