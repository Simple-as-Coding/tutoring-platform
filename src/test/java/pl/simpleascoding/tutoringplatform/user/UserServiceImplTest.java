package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.simpleascoding.tutoringplatform.user.exception.UserNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        UserNotFoundException exception
                = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(ID_1L));

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
        UserNotFoundException exception
                = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserByUsername(USERNAME));

        assertThat(exception.getMessage(), is(equalTo("User "+ USERNAME + " not found")));
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


    @DisplayName("confirmUserRegistration")
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

    @DisplayName("confirmUserRegistration")
    @Test
    void whenConfirmUserRegistration_thenTokenNotFoundExceptionShouldBeThrown() {
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.empty());
        //given
        Token tokenEntity = createTokenEntity();

        //when
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
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //when

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
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //when

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
        given(tokenRepository.findTokenByValue(tokenEntity.getValue())).willReturn(Optional.of(tokenEntity));

        //when
        UserAlreadyEnabledException exception = assertThrows(UserAlreadyEnabledException.class,
                () -> userServiceImpl.confirmUserRegistration(tokenEntity.getValue()));

        //then
        verify(tokenRepository, times(1)).findTokenByValue(tokenEntity.getValue());
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

}