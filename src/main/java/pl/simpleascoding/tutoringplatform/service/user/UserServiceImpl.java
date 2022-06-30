package pl.simpleascoding.tutoringplatform.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.domain.token.Token;
import pl.simpleascoding.tutoringplatform.domain.token.TokenType;
import pl.simpleascoding.tutoringplatform.domain.user.Role;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.exception.*;
import pl.simpleascoding.tutoringplatform.repository.TokenRepository;
import pl.simpleascoding.tutoringplatform.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    private static final String CONFIRM_REGISTER_MAIL_SUBJECT = "Confirm your email";
    private static final String CONFIRM_REGISTER_MAIL_TEXT = "Hi %s, please visit the link below to confirm your email address and activate your account: \n%s";
    private static final String CONFIRM_REGISTER_URL = "%s/confirm-registration?tokenValue=%s";

    @Override
    @Transactional
    public String createUser(CreateUserDTO dto, String rootUrl) {
        userRepository.findByUsername(dto.username()).ifPresent(user -> {
            throw new UsernameTakenException(dto.username());
        });
        User user = new User(dto.username(), dto.password(), dto.name(), dto.surname(), dto.email());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role(RoleType.USER));

        Token token = new Token(UUID.randomUUID().toString(), TokenType.REGISTER);
        user.getTokens().add(token);

        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.email());
        message.setSubject(CONFIRM_REGISTER_MAIL_SUBJECT);
        message.setText(String.format(CONFIRM_REGISTER_MAIL_TEXT, dto.name(), String.format(CONFIRM_REGISTER_URL, rootUrl, token.getValue())));

        mailSender.send(message);

        return HttpStatus.OK.getReasonPhrase();
    }

    @Override
    @Transactional
    public String confirmUserRegistration(String tokenValue) {
        Token token = tokenRepository.findTokenByValue(tokenValue).orElseThrow(TokenNotFoundException::new);

        if (token.getType() != TokenType.REGISTER) {
            throw new InvalidTokenException();
        }

        if (token.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedException();
        }

        if (token.getUser().isEnabled()) {
            throw new UserAlreadyEnabledException(token.getUser().getUsername());
        }

        token.getUser().setEnabled(true);
        token.confirm();

        return HttpStatus.OK.getReasonPhrase();
    }

}
