package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

@Immutable
public record CreateUserDTO(String username, String password, String name, String surname, String email) {
}
