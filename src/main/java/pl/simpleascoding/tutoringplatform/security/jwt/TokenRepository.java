package pl.simpleascoding.tutoringplatform.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByValue(String value);

    List<Token> findTokensByExpiresAtBefore(LocalDateTime localDateTime);
}
