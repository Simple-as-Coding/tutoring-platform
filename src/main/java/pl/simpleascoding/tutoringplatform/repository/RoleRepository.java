package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    //todo
}
