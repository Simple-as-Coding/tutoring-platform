package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

@Immutable
public record UpdateReviewDTO(@Size(max = 400) String content, @Range(min = 1, max = 5) byte stars) {
}
