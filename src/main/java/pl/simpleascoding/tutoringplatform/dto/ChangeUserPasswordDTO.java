package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

@Immutable
public record ChangeUserPasswordDTO(String oldPassword, String newPassword, String newPasswordReturn) {
}
