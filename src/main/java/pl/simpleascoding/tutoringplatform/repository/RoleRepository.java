package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.user.Role;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByRoleType(RoleType roleType);
}
