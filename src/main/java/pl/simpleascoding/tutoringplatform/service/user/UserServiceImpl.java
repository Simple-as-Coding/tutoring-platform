package pl.simpleascoding.tutoringplatform.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.domain.token.Token;
import pl.simpleascoding.tutoringplatform.domain.token.TokenType;
import pl.simpleascoding.tutoringplatform.domain.user.Role;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.exception.*;
import pl.simpleascoding.tutoringplatform.repository.TokenRepository;
import pl.simpleascoding.tutoringplatform.repository.UserRepository;

import java.util.Optional;

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
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional
    public String createUser(CreateUserDTO dto, String rootUrl) {
        userRepository.findUserByUsername(dto.username()).ifPresent(user -> {
            throw new UsernameTakenException(dto.username());
        });

        userRepository.findUserByEmail(dto.email()).ifPresent(user -> {
            throw new EmailTakenException(dto.email());
        });

        User user = new User(dto.username(), dto.password(), dto.name(), dto.surname(), dto.email());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role(RoleType.USER));

        Token token = new Token(TokenType.REGISTER);
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

    /**
     * Service method for changing the user's password.<br />
     * The password is first checked for correctness of the entered data by the user.
     * Then the currently specified password is checked against the one in storage.
     *
     * @param newPasswordsData User-provided multiple password data
     * @param username         Username of the user
     * @return The status of the operation
     */
    @Override
    @Transactional
    public String changeUserPassword(ChangeUserPasswordDTO newPasswordsData, String username) {
        User userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (isChangeAllowed(userEntity.getPassword(), newPasswordsData)) {
            userEntity.setPassword(passwordEncoder.encode(newPasswordsData.newPassword()));

            return HttpStatus.OK.getReasonPhrase();
        } else {
            return HttpStatus.UNAUTHORIZED.getReasonPhrase();
        }
    }

    @Override
    public boolean checkUserExists(Long id) {
        return userRepository.existsById(id);
    }

    private boolean isChangeAllowed(String passwordFromEntity, ChangeUserPasswordDTO newData) {
        boolean dataConfirmed = newData.newPassword().equals(newData.confirmationPassword());
        boolean userVerified = passwordEncoder.matches(newData.oldPassword(), passwordFromEntity);

        return userVerified && dataConfirmed;
    }

}
