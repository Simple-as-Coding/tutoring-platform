package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Range;

@Immutable
public record UpdateReviewDTO(String content, @Range(min = 1, max = 5) byte stars) {
}
