package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

@Immutable
public record ModifyUserDTO(String name, String surname) {
}
