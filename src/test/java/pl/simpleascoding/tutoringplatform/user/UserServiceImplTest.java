package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.simpleascoding.tutoringplatform.security.jwt.Token;
import pl.simpleascoding.tutoringplatform.security.jwt.TokenRepository;
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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest implements UserServiceMethodsForTests {

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

    @DisplayName("confirmUserRegistration")
    @Test
    void whenConfirmUserRegistration_thenUserRegistrationConfirmedShouldBeReturned() {
        //given
        Token tokenEntity = createTokenEntity();
        RscpDTO<Object> expectedRscpDTO = new RscpDTO<>(RscpStatus.OK, "User registration confirmed.", null);

        //when
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //then
        RscpDTO<?> resultRscpDTO = userServiceImpl.confirmUserRegistration(tokenEntity.getValue());
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(resultRscpDTO, is(equalTo(expectedRscpDTO)));
    }

    @DisplayName("confirmUserRegistration")
    @Test
    void whenConfirmUserRegistration_thenTokenNotFoundExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();

        //when
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.empty());

        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("Token not found")));
    }

    @DisplayName("confirmUserRegistration")
    @Test
    void whenConfirmUserRegistration_thenInvalidTokenExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.setType(null);

        //when
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));
        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("Invalid token")));
    }

    @DisplayName("confirmUserRegistration")
    @Test
    void whenConfirmUserRegistration_thenTokenAlreadyConfirmedExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        //when
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        TokenAlreadyConfirmedException exception = assertThrows(TokenAlreadyConfirmedException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));
//        then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("Token already confirmed")));
    }

    @DisplayName("confirmUserRegistration")
    @Test
    void whenConfirmUserRegistration_thenUserAlreadyEnabledExceptionShouldBeThrown() {
        //given
        Token tokenEntity = createTokenEntity();
        tokenEntity.getUser().setEnabled(true);

        //when
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));
        UserAlreadyEnabledException exception = assertThrows(UserAlreadyEnabledException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
        assertThat(exception.getMessage(), is(equalTo("User TestUser is already enabled")));
    }

}
