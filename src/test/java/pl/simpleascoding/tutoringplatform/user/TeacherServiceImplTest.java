package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.simpleascoding.tutoringplatform.user.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.user.exception.UserIsAlreadyATeacherException;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpStatus;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    private static final long ID_1L = 1L;

    private static final String USERNAME = "TestUserName";

    private static final String PASSWORD = "Password1!";

    private static final String NAME = "TestName";

    private static final String SURNAME = "TestSurname";

    private static final String EMAIL = "account@server.com";

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("Should return RscpDTO with message that teacher role has been added to given user")
    void whenAddTeacherRoleToUser_thenShouldReturnThatUserIsNowATeacher() {
        //given
        User user = createUserEntity();
        SignAsTeacherDTO teacherDTO = new SignAsTeacherDTO(USERNAME);
        given(userService.getUserByUsername(USERNAME)).willReturn(user);
        RscpDTO expected = new RscpDTO(RscpStatus.OK, "Teacher role added to user.", null);

        //when
        RscpDTO<?> result = teacherService.addTeacherRoleToUser(teacherDTO);

        //then
        verify(userService, times(1)).getUserByUsername(USERNAME);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return exception with message user is already a teacher")
    void whenAddTeacherRoleToUser_thenShouldReturnUserIsAlreadyATeacherException() {
        //given
        User user = createUserEntity();
        user.setRoles(Set.of(RoleType.TEACHER));
        SignAsTeacherDTO teacherDTO = new SignAsTeacherDTO(USERNAME);
        given(userService.getUserByUsername(USERNAME)).willReturn(user);

        //when
        Throwable thrown = catchThrowable(() -> teacherService.addTeacherRoleToUser(teacherDTO));

        //then
        verify(userService, times(1)).getUserByUsername(USERNAME);
        assertThat(thrown)
                .isInstanceOf(UserIsAlreadyATeacherException.class)
                .hasMessage("User is already a teacher");
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