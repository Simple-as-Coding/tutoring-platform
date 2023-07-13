package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.simpleascoding.tutoringplatform.user.exception.UserNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


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

}
