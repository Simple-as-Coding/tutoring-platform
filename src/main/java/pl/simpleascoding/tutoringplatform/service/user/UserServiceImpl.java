package pl.simpleascoding.tutoringplatform.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.domain.token.Token;
import pl.simpleascoding.tutoringplatform.domain.token.TokenType;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.*;
import pl.simpleascoding.tutoringplatform.exception.*;
import pl.simpleascoding.tutoringplatform.repository.TokenRepository;
import pl.simpleascoding.tutoringplatform.repository.UserRepository;
import pl.simpleascoding.tutoringplatform.rscp.RscpStatus;

@Service
@Primary
@RequiredArgsConstructor
class UserServiceImpl implements UserService {


    private static final String REGISTRATION_MAIL_SUBJECT = "Confirm your email";
    private static final String REGISTRATION_MAIL_TEXT = "Hi %s, please visit the link below to confirm your email address and activate your account: \n%s";
    private static final String REGISTRATION_LINK = "%s/confirm-registration?tokenValue=%s";
    private static final String USER_NOT_FOUND_MSG = "User with \"%s\" username, has not been found";
    private static final String PASSWORD_CHANGE_CONFIRMATION_MAIL_SUBJECT = "Confirm your password change";
    private static final String PASSWORD_CHANGE_CONFIRMATION_MAIL_TEXT = "Hi %s, please visit the link below to confirm your password change: \n%s";
    private static final String PASSWORD_CHANGE_CONFIRMATION_LINK = "%s/confirm-change-password?tokenValue=%s";


    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final UserModelMapper userModelMapper;

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    @Transactional
    public RscpDTO<?> createUser(CreateUserDTO dto, String rootUrl) {
        userRepository.findUserByUsername(dto.username()).ifPresent(user -> {
            throw new UsernameTakenException(dto.username());
        });

        userRepository.findUserByEmail(dto.email()).ifPresent(user -> {
            throw new EmailTakenException(dto.email());
        });

        User user = new User(dto.username(), dto.password(), dto.name(), dto.surname(), dto.email());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(RoleType.USER);

        Token token = new Token(TokenType.REGISTER_CONFIRMATION);
        user.getTokens().add(token);

        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.email());
        message.setSubject(REGISTRATION_MAIL_SUBJECT);
        message.setText(String.format(REGISTRATION_MAIL_TEXT, dto.name(),
                String.format(REGISTRATION_LINK, rootUrl, token.getValue())));

        mailSender.send(message);


        return new RscpDTO<>(RscpStatus.OK, "User creation completed successfully", null);
    }

    @Override
    @Transactional
    public RscpDTO<?> confirmUserRegistration(String tokenValue) {
        Token token = tokenRepository.findTokenByValue(tokenValue).orElseThrow(TokenNotFoundException::new);

        if (token.getType() != TokenType.REGISTER_CONFIRMATION) {
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


        return new RscpDTO<>(RscpStatus.OK, "User registration confirmed.", null);
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
    public RscpDTO<?> changeUserPassword(ChangeUserPasswordDTO newPasswordsData, String username, String rootUrl) {
        User userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (isChangeAllowed(userEntity.getPassword(), newPasswordsData)) {

            Token token = new Token(TokenType.CHANGE_PASSWORD_CONFIRMATION);
            String password = newPasswordsData.newPassword();
            token.setData(passwordEncoder.encode(password));
            userEntity.getTokens().add(token);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEntity.getEmail());
            message.setSubject(PASSWORD_CHANGE_CONFIRMATION_MAIL_SUBJECT);
            message.setText(String.format(PASSWORD_CHANGE_CONFIRMATION_MAIL_TEXT, userEntity.getName(),
                    String.format(PASSWORD_CHANGE_CONFIRMATION_LINK, rootUrl, token.getValue())));

            mailSender.send(message);


            return new RscpDTO<>(RscpStatus.OK, "Email with confirmation was sent.", null);
        } else {

            return new RscpDTO<>(RscpStatus.UNAUTHORIZED, null, null);
        }
    }

    @Override
    @Transactional
    public RscpDTO<?> confirmChangeUserPassword(String tokenValue) {
        Token token = tokenRepository.findTokenByValue(tokenValue).orElseThrow(TokenNotFoundException::new);

        if (token.getType() != TokenType.CHANGE_PASSWORD_CONFIRMATION) {
            throw new InvalidTokenException();
        }

        if (token.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedException();
        }
        String password = token.getData();
        token.getUser().setPassword(password);

        token.confirm();


        return new RscpDTO<>(RscpStatus.OK, "Password change completed successfully.", null);
    }

    public RscpDTO<UserDTO> modifyUser(ModifyUserDTO dto, String username) {
        User userEntity = getUserByUsername(username);

        if (dto.name() != null && !dto.name().isEmpty()) {
            userEntity.setName(dto.name());
        }
        if (dto.surname() != null && !dto.surname().isEmpty()) {
            userEntity.setSurname(dto.surname());
        }

        return new RscpDTO<>(RscpStatus.OK, "User modification completed successfully.", userModelMapper.mapUserEntityToUserDTO(userEntity));
    }

    @Override
    public boolean checkUserExists(long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    private boolean isChangeAllowed(String passwordFromEntity, ChangeUserPasswordDTO newData) {
        boolean dataConfirmed = newData.newPassword().equals(newData.confirmationPassword());
        boolean userVerified = passwordEncoder.matches(newData.oldPassword(), passwordFromEntity);

        return userVerified && dataConfirmed;
    }

}
