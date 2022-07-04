package pl.simpleascoding.tutoringplatform.service.token;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.domain.token.Token;
import pl.simpleascoding.tutoringplatform.domain.token.TokenType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.repository.TokenRepository;
import pl.simpleascoding.tutoringplatform.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpiredTokensCleanerServiceImpl implements ExpiredTokensCleanerService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    @Scheduled(cron = "0 0 * ? * *")
    public void cleanExpiredTokens() {
        List<Token> expiredTokens = tokenRepository.findTokensByExpiresAtBefore(LocalDateTime.now());
        List<User> inactiveUsers = expiredTokens
                .stream()
                .filter(token -> TokenType.REGISTER.equals(token.getType()) && Optional.ofNullable(token.getConfirmedAt()).isEmpty())
                .map(Token::getUser)
                .toList();

        tokenRepository.deleteAll(expiredTokens);
        userRepository.deleteAll(inactiveUsers);
    }
}
