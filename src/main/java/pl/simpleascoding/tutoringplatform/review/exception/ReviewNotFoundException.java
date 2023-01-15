package pl.simpleascoding.tutoringplatform.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends IllegalArgumentException {

    private static final String MESSAGE_REVIEW_WITH_ID_NOT_FOUND = "Review with id %d not found";

    public ReviewNotFoundException(long id) {
        super(String.format(MESSAGE_REVIEW_WITH_ID_NOT_FOUND, id));
    }
}
