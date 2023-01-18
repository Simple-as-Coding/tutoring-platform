package pl.simpleascoding.tutoringplatform.review.dto;

import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;

import java.time.Instant;

public record Review(long id, String content, byte stars, UserDTO author, UserDTO receiver, Instant createdAt) {
}
