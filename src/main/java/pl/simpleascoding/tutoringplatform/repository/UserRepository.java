package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    boolean existsById(long id);

    Optional<User> findUserByEmail(String email);

}
