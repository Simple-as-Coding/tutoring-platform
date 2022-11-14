package pl.simpleascoding.tutoringplatform.dto;

import java.time.Instant;

public record ReviewDTO(long id, String content, byte stars, UserDTO author, UserDTO receiver, Instant createdAt) {
}
