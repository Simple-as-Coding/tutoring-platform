package pl.simpleascoding.tutoringplatform.dto;

import pl.simpleascoding.tutoringplatform.domain.user.RoleType;

import java.util.Set;

public record UserDTO(Long id, String username, String name, String surname, String email, Set<RoleType> roles) {
}
