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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private  UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @DisplayName("Should return user with given id")
    @Test
    void whenGetUserById_thenCorrectUserShouldBeReturned() {
        //given
        User user = createUserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        User result = userServiceImpl.getUserById(1L);

        //then
        assertThat(result, is(equalTo(user)));
    }

    @DisplayName("Should throw UserNotFoundException when user with given id does not exist")
    @Test
    void whenGetUserById_thenUserNotFoundExceptionShouldBeThrown() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //when & then
        UserNotFoundException exception =
                assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(1L));

        assertThat(exception.getMessage(), is(equalTo("User with id 1 not found")));
    }

    private User createUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        user.setPassword("password");
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("test@mail.com");
        return user;
    }
}
