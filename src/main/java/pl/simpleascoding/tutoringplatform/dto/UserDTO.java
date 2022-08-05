package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

@Immutable
public record UserDTO(Long id, String username, String name, String surname, String email) {
}
