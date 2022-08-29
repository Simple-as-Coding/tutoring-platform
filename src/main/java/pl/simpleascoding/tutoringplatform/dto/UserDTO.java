package pl.simpleascoding.tutoringplatform.dto;

import org.hibernate.annotations.Immutable;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;

import java.util.Set;

@Immutable
public record UserDTO(Long id, String username, String name, String surname, String email, Set<RoleType> roles) {
}
