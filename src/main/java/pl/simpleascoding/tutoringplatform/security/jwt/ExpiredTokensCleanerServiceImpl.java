package pl.simpleascoding.tutoringplatform.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.user.User;
import pl.simpleascoding.tutoringplatform.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
class ExpiredTokensCleanerServiceImpl implements ExpiredTokensCleanerService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    @Scheduled(cron = "0 0 * ? * *")
    public void cleanExpiredTokens() {
        List<Token> expiredTokens = tokenRepository.findTokensByExpiresAtBefore(LocalDateTime.now());
        List<User> inactiveUsers = expiredTokens
                .stream()
                .filter(token -> TokenType.REGISTER_CONFIRMATION.equals(token.getType()) && Optional.ofNullable(token.getConfirmedAt()).isEmpty())
                .map(Token::getUser)
                .toList();

        tokenRepository.deleteAll(expiredTokens);
        log.info("Deleted expired tokens from the database.");
        userRepository.deleteAll(inactiveUsers);
        String logMsg = "Deleted user {}. Reason: Account has not been been activated.";
        inactiveUsers.forEach(user -> log.info(logMsg, user.getUsername()));
    }
}
