package pl.simpleascoding.tutoringplatform.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.user.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;
import pl.simpleascoding.tutoringplatform.user.exception.UserIsAlreadyATeacherException;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    private static final long USER_ID_1L = 1L;

    private static final String USERNAME = "UserName";

    private static final String PASSWORD = "Password1!";

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final String EMAIL = "account@server.com";

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    @Spy
    private UserModelMapperImpl userModelMapper;


    @Test
    @DisplayName("Should return RscpDTO with RscpStatus.OK and message that teacher role has been added to given user")
    void whenAddTeacherRoleToUser_thenShouldReturnThatUserIsNowATeacher() {
        //given
        User user = createUserEntity();
        SignAsTeacherDTO teacherDTO = new SignAsTeacherDTO(USERNAME);
        given(userService.getUserByUsername(USERNAME)).willReturn(user);
        RscpDTO<?> expected = new RscpDTO<>(RscpStatus.OK, "Teacher role added to user.", null);

        //when
        RscpDTO<?> result = teacherService.addTeacherRoleToUser(teacherDTO);

        //then
        verify(userService, times(1)).getUserByUsername(USERNAME);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return exception UserIsAlreadyATeacherException")
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
                .isInstanceOf(UserIsAlreadyATeacherException.class);
    }

    @Test
    @DisplayName("Should return RscpDTO with RscpStatus.OK and message that Page returned")
    void whenFindAllTeachers_thenShouldReturnTeachers() {
        //given
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        List<User> listOfTeachers = createListOfTeachers(size);
        Page<User> userPage = new PageImpl<>(listOfTeachers);
        given(userRepository.findUsersByRolesContaining(RoleType.TEACHER, pageable)).willReturn(userPage);
        List<UserDTO> expectedUserDto = convertUserToUserDto(listOfTeachers);

        //when
        RscpDTO<Page<UserDTO>> result = teacherService.findAllTeachers(pageable);

        //then
        verify(userRepository, times(1))
                .findUsersByRolesContaining(RoleType.TEACHER, pageable);
        assertAll(
                () -> assertThat(Objects.requireNonNull(result.body()).getTotalElements()).isEqualTo(size),
                () -> assertThat(result.status()).isEqualTo(RscpStatus.OK),
                () -> assertThat(result.message()).isEqualTo("Page returned."),
                () -> assertThat(Objects.requireNonNull(result.body()).getContent()).isEqualTo(expectedUserDto)
        );
    }

    private List<User> createListOfTeachers(int numberOfTeachers) {
        List<User> teachers = new ArrayList<>();

        for (int i = 1; i <= numberOfTeachers; i++) {
            User teacher = new User();
            teacher.setId((long) i);
            teacher.setUsername(USERNAME + i);
            teacher.setPassword(PASSWORD + i);
            teacher.setName(NAME + i);
            teacher.setSurname(SURNAME + i);
            teacher.setEmail(i + EMAIL);
            teacher.setRoles(Set.of(RoleType.TEACHER));
            teachers.add(teacher);
        }

        return teachers;
    }

    private User createUserEntity() {
        User user = new User();
        user.setId(USER_ID_1L);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setEmail(EMAIL);

        return user;
    }

    private List<UserDTO> convertUserToUserDto(List<User> users) {
        return users.stream().map(user -> new UserModelMapperImpl().mapUserEntityToUserDTO(user)).toList();
    }

}
