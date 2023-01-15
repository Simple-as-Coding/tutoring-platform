package pl.simpleascoding.tutoringplatform.review.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public record CreateReviewDTO(@Positive long receiverId, @Size(max = 400) String content,
                              @Range(min = 1, max = 5) byte stars) {
}
