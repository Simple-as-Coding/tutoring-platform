package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.userDetails.UserDetailsImpl;

import java.util.Optional;

public interface UserDetailsImplRepository extends JpaRepository<UserDetailsImpl, Long> {
    Optional<UserDetailsImpl> findByUser_Username(String username);
}
