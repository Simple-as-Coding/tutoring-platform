package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    //todo
}
