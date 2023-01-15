package pl.simpleascoding.tutoringplatform.user.dto;

import pl.simpleascoding.tutoringplatform.user.RoleType;

import java.util.Set;

public record UserDTO(Long id, String username, String name, String surname, String email, Set<RoleType> roles) {
}
