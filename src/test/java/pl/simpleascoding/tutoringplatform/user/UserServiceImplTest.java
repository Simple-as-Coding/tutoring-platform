package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.simpleascoding.tutoringplatform.security.jwt.Token;
import pl.simpleascoding.tutoringplatform.security.jwt.TokenRepository;
import pl.simpleascoding.tutoringplatform.security.jwt.TokenType;
import pl.simpleascoding.tutoringplatform.security.jwt.exception.InvalidTokenException;
import pl.simpleascoding.tutoringplatform.security.jwt.exception.TokenAlreadyConfirmedException;
import pl.simpleascoding.tutoringplatform.security.jwt.exception.TokenNotFoundException;
import pl.simpleascoding.tutoringplatform.user.exception.UserAlreadyEnabledException;
import pl.simpleascoding.tutoringplatform.user.exception.UserNotFoundException;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final long ID_1L = 1L;

    private static final String USERNAME = "TestUser";

    private static final String PASSWORD = "Password1!";

    private static final String NAME = "TestName";

    private static final String SURNAME = "TestSurname";

    private static final String EMAIL = "test@mail.com";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JavaMailSender mailSender;
    @Spy
    UserModelMapper userModelMapper;


    @DisplayName("Should return user with given id")
    @Test
    void whenGetUserById_thenCorrectUserShouldBeReturned() {
        //given
        User user = createUserEntity();
        when(userRepository.findById(ID_1L)).thenReturn(Optional.of(user));

        //when
        User result = userServiceImpl.getUserById(ID_1L);

        //then
        assertThat(result, is(equalTo(user)));
    }

    @DisplayName("Should throw UserNotFoundException when user with given id does not exist")
    @Test
    void whenGetUserById_thenUserNotFoundExceptionShouldBeThrown() {
        //given
        when(userRepository.findById(ID_1L)).thenReturn(Optional.empty());

        //when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(ID_1L));

        assertThat(exception.getMessage(), is(equalTo("User with id 1 not found")));
    }

    @DisplayName("Should return user with given username")
    @Test
    void whenGetUserByUsername_thenCorrectUserShouldBeReturned() {
        //given
        User user = createUserEntity();
        when(userRepository.findUserByUsername("TestUser")).thenReturn(Optional.of(user));

        //when
        User result = userServiceImpl.getUserByUsername("TestUser");

        //then
        assertThat(result, is(equalTo(user)));
    }

    @DisplayName("Should throw UserNotFoundException when user with given username does not exist")
    @Test
    void whenGetUserByUsername_thenUserNotFoundExceptionShouldBeThrown() {
        //given
        when(userRepository.findUserByUsername(USERNAME)).thenReturn(Optional.empty());

        //when & then
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserByUsername(USERNAME));
    }

    @DisplayName("Should return null and User registration confirmed.")
    @Test
    void whenConfirmUserRegistration_thenUserRegistrationConfirmedShouldBeReturned() {
        //given
        Token tokenEntity = createTokenEntity();

        when(tokenRepository.findTokenByValue(tokenEntity.getValue())).thenReturn(Optional.of(tokenEntity));
        //when
        RscpDTO<?> resultRscpDTO = userService.confirmUserRegistration(tokenEntity.getValue());

        RscpDTO<Object> expectedRscpDTO = new RscpDTO<>(RscpStatus.OK, "User registration confirmed.", null);

        assertThat(resultRscpDTO, is(equalTo(expectedRscpDTO)));
    }

    @DisplayName("Should throw TokenNotFoundException when token does not exist")
    @Test
    void whenConfirmUserRegistration_thenTokenNotFoundExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();

        //when & then
        when(tokenRepository.findTokenByValue(tokenEntity.getValue())).thenReturn(Optional.empty());

        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> userService.confirmUserRegistration(tokenEntity.getValue()));

        assertThat(exception.getMessage(), is(equalTo("Token not found")));
    }

    @DisplayName("Should throw InvalidTokenException when token is invalid")
    @Test
    void whenConfirmUserRegistration_thenInvalidTokenExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.setType(null);
        //when & then
        when(tokenRepository.findTokenByValue(tokenEntity.getValue())).thenReturn(Optional.of(tokenEntity));

        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> userService.confirmUserRegistration(tokenEntity.getValue()));

        assertThat(exception.getMessage(), is(equalTo("Invalid token")));
    }

    @DisplayName("Should throw TokenAlreadyConfirmedException when token is already confirmed")
    @Test
    void whenConfirmUserRegistration_thenTokenAlreadyConfirmedExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        //when & then
        when(tokenRepository.findTokenByValue(tokenEntity.getValue())).thenReturn(Optional.of(tokenEntity));

        TokenAlreadyConfirmedException exception = assertThrows(TokenAlreadyConfirmedException.class,
                () -> userService.confirmUserRegistration(tokenEntity.getValue()));

        assertThat(exception.getMessage(), is(equalTo("Token already confirmed")));
    }

    @DisplayName("Should throw UserAlreadyEnabledException when token is already registered")
    @Test
    void whenConfirmUserRegistration_thenUserAlreadyEnabledExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.getUser().setEnabled(true);
        //when & then
        when(tokenRepository.findTokenByValue(tokenEntity.getValue())).thenReturn(Optional.of(tokenEntity));

        UserAlreadyEnabledException exception = assertThrows(UserAlreadyEnabledException.class,
                () -> userService.confirmUserRegistration(tokenEntity.getValue()));

        assertThat(exception.getMessage(), is(equalTo("User TestUser is already enabled")));
    }



    private User createUserEntity() {
        User user = new User();
        user.setId(ID_1L);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setEmail(EMAIL);
        return user;
    }

    private Token createTokenEntity() {
        Token token = new Token();
        token.setType(TokenType.REGISTER_CONFIRMATION);
        token.setUser(createUserEntity());
        return token;
    }

}
