package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

@Immutable
public record ChangeUserPasswordDTO(String username, String oldPassword, String newPassword) {
}
