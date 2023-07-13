package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final long ID_1L = 1L;

    private static final String USERNAME = "TestUserName";

    private static final String PASSWORD = "Password1!";

    private static final String NAME = "TestName";

    private static final String SURNAME = "TestSurname";

    private static final String EMAIL = "account@server.com";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private TokenRepository tokenRepository;


    @DisplayName("Should return user with given id")
    @Test
    void whenGetUserById_thenCorrectUserShouldBeReturned() {
        //given
        User user = createUserEntity();
        given(userRepository.findById(ID_1L)).willReturn(Optional.of(user));

        //when
        User result = userServiceImpl.getUserById(ID_1L);

        //then
        assertThat(result, is(equalTo(user)));
    }

    @DisplayName("Should throw UserNotFoundException when user with given id does not exist")
    @Test
    void whenGetUserById_thenUserNotFoundExceptionShouldBeThrown() {
        //given
        given(userRepository.findById(ID_1L)).willReturn(Optional.empty());

        //when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userServiceImpl.getUserById(ID_1L));

        assertThat(exception.getMessage(), is(equalTo("User with id " + ID_1L + " not found")));
    }

    @DisplayName("Should return user with given username")
    @Test
    void whenGetUserByUsername_thenCorrectUserShouldBeReturned() {
        //given
        User user = createUserEntity();
        given(userRepository.findUserByUsername(USERNAME)).willReturn(Optional.of(user));

        //when
        User result = userServiceImpl.getUserByUsername(USERNAME);

        //then
        assertThat(result, is(equalTo(user)));
    }

    @DisplayName("Should throw UserNotFoundException when user with given username does not exist")
    @Test
    void whenGetUserByUsername_thenUserNotFoundExceptionShouldBeThrown() {
        //given
        given(userRepository.findUserByUsername(USERNAME)).willReturn(Optional.empty());

        //when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userServiceImpl.getUserByUsername(USERNAME));

        assertThat(exception.getMessage(), is(equalTo("User " + USERNAME + " not found")));
    }

    @DisplayName("Should return true when user with given id exists")
    @Test
    void whenUserExistsById_thenTrueShouldBeReturned() {
        //given
        given(userRepository.existsById(ID_1L)).willReturn(true);

        //when
        boolean result = userServiceImpl.checkUserExists(ID_1L);

        //then
        assertThat(result, is(true));
    }


    @DisplayName("Should return false when user with given id does not exist")
    @Test
    void whenUserExistsById_thenFalseShouldBeReturned() {
        //given
        given(userRepository.existsById(ID_1L)).willReturn(false);

        //when
        boolean result = userServiceImpl.checkUserExists(ID_1L);

        //then
        assertThat(result, is(false));
    }


    @DisplayName("Should return true when user with given username exists")
    @Test
    void whenConfirmUserRegistration_thenUserRegistrationConfirmedShouldBeReturned() {
        //given
        Token tokenEntity = createTokenEntity();
        RscpDTO<Object> expectedRscpDTO = new RscpDTO<>(RscpStatus.OK, "User registration confirmed.", null);
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //when
        RscpDTO<?> resultRscpDTO = userServiceImpl.confirmUserRegistration(tokenEntity.getValue());

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(resultRscpDTO, is(equalTo(expectedRscpDTO)));
    }

    @DisplayName("Should throw TokenNotFoundException when token with given value does not exist")
    @Test
    void whenConfirmUserRegistration_thenTokenNotFoundExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.empty());

        //when
        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("Token not found")));
    }

    @DisplayName("Should throw InvalidTokenException when token with given value is invalid")
    @Test
    void whenConfirmUserRegistration_thenInvalidTokenExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.setType(null);
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //when
        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("Invalid token")));
    }

    @DisplayName("Should throw TokenExpiredException when token with given value is expired")
    @Test
    void whenConfirmUserRegistration_thenTokenAlreadyConfirmedExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //when
        TokenAlreadyConfirmedException exception = assertThrows(TokenAlreadyConfirmedException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("Token already confirmed")));
    }

    @DisplayName("Should throw UserAlreadyEnabledException when user with given username is already enabled")
    @Test
    void whenConfirmUserRegistration_thenUserAlreadyEnabledExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.getUser().setEnabled(true);
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //when
        UserAlreadyEnabledException exception = assertThrows(UserAlreadyEnabledException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("User " + USERNAME + " is already enabled")));
    }

    @DisplayName("Should return user with given username")
    @Test
    void whenLoadUserByUsername_thenCorrectUserShouldBeReturned() {
        //given
        User user = createUserEntity();
        given(userRepository.findUserByUsername(USERNAME)).willReturn(Optional.of(user));

        //when
        UserDetails result = userServiceImpl.loadUserByUsername(USERNAME);

        //then
        assertThat(result, is(equalTo(user)));
    }

    @DisplayName("Should throw UsernameNotFoundException when user with given username does not exist")
    @Test
    void whenLoadUserByUsername_thenUsernameNotFoundExceptionShouldBeThrown() {
        //given
        given(userRepository.findUserByUsername(USERNAME)).willReturn(Optional.empty());

        //when & then
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername(USERNAME));
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