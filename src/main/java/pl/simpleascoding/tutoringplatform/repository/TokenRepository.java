package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.token.Token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByValue(String value);

    List<Token> findTokensByExpiresAtBefore(LocalDateTime localDateTime);
}
