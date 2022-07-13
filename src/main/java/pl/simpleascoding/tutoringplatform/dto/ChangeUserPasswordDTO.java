package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;

/**
 * Record used to transfer data (passwords) between the service and the controller
 *
 * @param oldPassword   The user's existing password
 * @param newPassword   New user password
 * @param confirmationPassword   Confirmation of the new user password
 */
@Immutable
public record ChangeUserPasswordDTO(String oldPassword, String newPassword, String confirmationPassword) {
}
