package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

@Immutable
public record SignAsTeacherDTO(String username) {
}
