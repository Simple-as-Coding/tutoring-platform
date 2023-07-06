package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.simpleascoding.tutoringplatform.security.jwt.TokenRepository;
import pl.simpleascoding.tutoringplatform.user.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;
import pl.simpleascoding.tutoringplatform.user.exception.UserNotFoundException;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpStatus;

import java.util.Optional;
import java.util.Set;

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

    @DisplayName("Should return user with correct data")
    @Test
    void whenCreateUser_thenCorrectDataShouldBeReturned() {
        //given

        UserRepository userRepository1 = new UserRepositoryTestImpl();
        TokenRepository tokenRepository = new TokenRepositoryTestImpl();
        PasswordEncoder passwordEncoder1 = mock(PasswordEncoder.class);
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        UserModelMapper userModelMapper1 = new UserModelMapperTestImpl();
        UserServiceImpl userService =
                new UserServiceImpl(userRepository1, tokenRepository, passwordEncoder1, javaMailSender, userModelMapper1);

        User user = createUserEntity();
        CreateUserDTO userDTO = new CreateUserDTO(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail());
        String rootUrl = "http://example.com";

        //when
        RscpDTO<UserDTO> resultUser = userService.createUser(userDTO, rootUrl);

        //then
        UserDTO expectedUserDto = new UserDTO(user.getId(), user.getUsername(),
                user.getName(), user.getSurname(), user.getEmail(), Set.of(RoleType.USER));

        RscpDTO<UserDTO> expectedUserRscpDTO = new RscpDTO<>(RscpStatus.OK, "User creation completed successfully", expectedUserDto);
        assertThat(resultUser, is(equalTo(expectedUserRscpDTO)));
    }

//    @DisplayName("Should return user with correct data")
//    @Test
//    void whenCreateUser_thenCorrectDataShouldBeReturned() {
//        //given
//        UserRepository userRepository1 = new UserRepositoryTestImpl();
//
//        User user = createUserEntity();
//        CreateUserDTO userDTO = new CreateUserDTO(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail());
//        String rootUrl = "http://example.com";
//
//        //when
//        RscpDTO<UserDTO> resultUser = userServiceImpl.createUser(userDTO, rootUrl);
//
//        //then
////        return new RscpDTO<>(RscpStatus.OK, "User creation completed successfully", userDTO);
//        UserDTO expectedUserDto = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), Set.of(RoleType.USER));
//
//        RscpDTO<UserDTO> expectedUserRscpDTO = new RscpDTO<>(RscpStatus.OK, "User creation completed successfully", expectedUserDto);
//        assertThat(resultUser, is(equalTo(expectedUserRscpDTO)));
//    }

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
