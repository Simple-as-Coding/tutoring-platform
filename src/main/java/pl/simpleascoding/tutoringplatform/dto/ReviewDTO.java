package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

import java.time.Instant;

@Immutable
public record ReviewDTO(long id, String content, byte stars, UserDTO author, UserDTO receiver, Instant createdAt) {
}
