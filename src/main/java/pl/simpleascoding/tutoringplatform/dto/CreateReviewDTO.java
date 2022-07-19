package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public record CreateReviewDTO(@NotNull Long receiverId, String content, @Range(min = 1, max = 5) byte stars) {
}
