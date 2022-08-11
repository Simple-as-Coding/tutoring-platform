package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

@Immutable
public record TeacherDTO(String name, String surname, String email) {
}
